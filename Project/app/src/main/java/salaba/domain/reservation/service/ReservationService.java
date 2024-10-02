package salaba.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import salaba.global.constants.ProcessStatus;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.reservation.repository.DiscountRepository;
import salaba.domain.reservation.repository.ReservationRepository;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.auth.exception.NoAuthorityException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findById(reqDto.getRentalHomeId()).orElseThrow(NoSuchElementException::new);

        // 예약 생성
        Reservation reservation = Reservation.create(reqDto.getStartDate(), reqDto.getEndDate(), rentalHome, member);
        // 예약 저장
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    public List<ReservedDateDto> getReservedDate(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
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

    public ReservationCompleteResDto completeReservation(Long memberId, PaymentReqDto paymentReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(paymentReqDto.getReservationId()).orElseThrow(NoSuchElementException::new);
        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("예약자와 회원이 일치하지 않습니다.");
        }

        List<Discount> discounts = new ArrayList<>();
        //할인이 있다면 할인을 만든다.
        if (paymentReqDto.getDiscounts() != null && !paymentReqDto.getDiscounts().isEmpty()) {
             discounts = paymentReqDto.getDiscounts().stream().map(reqDto ->
                    Discount.create(reservation, reqDto.getAmount(), reqDto.getContent())).toList();
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
        reservation.complete(paymentReqDto.getPaymentCode() ,paymentReqDto.getMethod());

        // 포인트를 적립시킨다.
        pointService.createPaymentPoint(reservation.getMember(), reservation.getFinalPrice());
        return new ReservationCompleteResDto(reservation);
    }

    public void cancelReservation(Long reservationId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(reservationId).orElseThrow(NoSuchElementException::new);
        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("예약자와 회원이 일치하지 않습니다.");
        }
        discountRepository.deleteByReservation(reservation);
        reservation.cancel();

    }
}
