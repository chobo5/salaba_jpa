package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.dto.ReservedDateDto;
import salaba.dto.request.ReservationReqDto;
import salaba.dto.response.ReservationToGuestResDto;
import salaba.dto.response.ReservationToHostResDto;
import salaba.util.ProcessStatus;
import salaba.entity.member.Member;
import salaba.entity.rental.Payment;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Reservation;
import salaba.repository.jpa.MemberRepository;
import salaba.repository.jpa.rentalHome.PaymentRepository;
import salaba.repository.jpa.rentalHome.ReservationRepository;
import salaba.repository.jpa.rentalHome.RentalHomeRepository;

import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final PaymentRepository paymentRepository;
    public Long makeReservation(Long memberId, ReservationReqDto reqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findById(reqDto.getRentalHomeId()).orElseThrow(NoSuchElementException::new);
        // 예약 생성
        Reservation reservation = Reservation.createReservation(reqDto.getStartDate(), reqDto.getEndDate(), rentalHome, member);
        // 예약 저장
        reservationRepository.save(reservation);

        //예약 일수(체크인, 체크아웃 날짜 차이)
        int daysBetween = Period.between(reqDto.getEndDate().toLocalDate(), reqDto.getStartDate().toLocalDate()).getDays();
        //이용 가격 계산
        int originalPrice = rentalHome.getPrice() * daysBetween;

        //결제 생성 및 저장(결제 미완료 상태)
        Payment payment = Payment.createPayment(reservation, originalPrice);
        paymentRepository.save(payment);
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

    public Page<ReservationToHostResDto> getWithGuest(Long rentalHomeId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithGuest(rentalHomeId, pageable);

        return reservations.map(ReservationToHostResDto::new);
    }

    public Page<ReservationToGuestResDto> getWithRentalHomeAndHost(Long memberId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithRentalHomeAndHost(memberId, pageable);

        return reservations.map(ReservationToGuestResDto::new);
    }
}
