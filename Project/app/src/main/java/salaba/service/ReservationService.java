package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.request.ReservationReqDto;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Reservation;
import salaba.repository.MemberRepository;
import salaba.repository.ReservationRepository;
import salaba.repository.rentalHome.RentalHomeRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    public Long makeReservation(ReservationReqDto reqDto) {
        Member member = memberRepository.findById(reqDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findById(reqDto.getRentalHomeId()).orElseThrow(NoSuchElementException::new);
        Reservation reservation = Reservation.createReservation(reqDto.getStartDate(), reqDto.getEndDate(), rentalHome, member);
        reservationRepository.save(reservation);
        return reservation.getId();
    }
}
