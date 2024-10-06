package salaba.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.member.service.PointService;
import salaba.domain.reservation.dto.request.PaymentReqDto;
import salaba.domain.reservation.dto.response.ReservationCompleteResDto;
import salaba.domain.reservation.entity.Discount;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.dto.response.ReservationResForGuestDto;
import salaba.domain.reservation.dto.response.ReservationResForHostDto;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.dto.ReservedDateDto;
import salaba.domain.reservation.dto.request.ReservationReqDto;
import salaba.domain.global.constants.ProcessStatus;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.reservation.repository.DiscountRepository;
import salaba.domain.reservation.repository.ReservationRepository;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.auth.exception.NoAuthorityException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final DiscountRepository discountRepository;
    private final PointService pointService;

    public Long makeReservation(Long memberId, ReservationReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        RentalHome rentalHome = rentalHomeRepository.findById(reqDto.getRentalHomeId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, reqDto.getRentalHomeId())));

        // 예약 생성
        Reservation reservation = Reservation.create(reqDto.getStartDate(), reqDto.getEndDate(), rentalHome, member);
        // 예약 저장
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    public List<ReservedDateDto> getReservedDate(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));

        List<Reservation> reservations = reservationRepository.findByRentalHomeAndStatus(rentalHome, ProcessStatus.COMPLETE);

        return reservations.stream()
                .map(reservation -> new ReservedDateDto(reservation.getStartDate().toLocalDate(),
                        reservation.getEndDate().toLocalDate().minusDays(1)))
                .collect(Collectors.toList());

    }

    public Page<ReservationResForHostDto> getWithRentalHomeForHost(Long rentalHomeId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithGuest(rentalHomeId, pageable);

        return reservations.map(ReservationResForHostDto::new);
    }

    public Page<ReservationResForGuestDto> getWithRentalHomeForGuest(Long memberId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithRentalHomeAndHost(memberId, pageable);

        return reservations.map(ReservationResForGuestDto::new);
    }

    public ReservationCompleteResDto completeReservation(Long memberId, PaymentReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(reqDto.getReservationId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reservation.class, reqDto.getReservationId())));

        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("예약자와 회원이 일치하지 않습니다.");
        }

        List<Discount> discounts = new ArrayList<>();
        //할인이 있다면 할인을 만든다.
        if (reqDto.getDiscounts() != null && !reqDto.getDiscounts().isEmpty()) {
             discounts = reqDto.getDiscounts().stream().map(dto ->
                    Discount.create(reservation, dto.getAmount(), dto.getContent())).toList();
            // 포인트를 사용했다면 포인트를 차감시킨다.
            discounts.forEach(discount -> {
                if (discount.getContent().contains("포인트")) {
                    pointService.createUsedPoint(member, discount);
                }
            });
            //할인을 저장한다.
            discountRepository.saveAll(discounts);
        }

        // 결제를 완료시킨다.
        reservation.complete(reqDto.getPaymentCode() ,reqDto.getMethod());

        // 포인트를 적립시킨다.
        pointService.createPaymentPoint(reservation.getMember(), reservation.getFinalPrice());
        return new ReservationCompleteResDto(reservation);
    }

    public void cancelReservation(Long reservationId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reservation.class, reservationId)));

        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("예약자와 회원이 일치하지 않습니다.");
        }

        discountRepository.deleteByReservation(reservation);
        reservation.cancel();

    }
}
