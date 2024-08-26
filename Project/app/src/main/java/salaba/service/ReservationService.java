package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.dto.ReservedDateDto;
import salaba.dto.request.ReservationReqDto;
import salaba.dto.response.ReservationResToGuestDto;
import salaba.dto.response.ReservationResToHostDto;
import salaba.entity.ProcessStatus;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Reservation;
import salaba.repository.MemberRepository;
import salaba.repository.rentalHome.ReservationRepository;
import salaba.repository.rentalHome.RentalHomeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public List<ReservedDateDto> getReservedDate(Long rentalHomeId, ProcessStatus status) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        List<Reservation> reservations = reservationRepository.findByRentalHomeAndStatus(rentalHome, status);
        return reservations.stream()
                .map(reservation -> new ReservedDateDto(reservation.getStartDate().toLocalDate(),
                        reservation.getEndDate().toLocalDate().minusDays(1)))
                .collect(Collectors.toList());

    }

    public Page<ReservationResToHostDto> getWithGuest(Long rentalHomeId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithGuest(rentalHomeId, pageable);

        return reservations.map(ReservationResToHostDto::new);
    }

    public Page<ReservationResToGuestDto> getWithRentalHomeAndHost(Long memberId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findWithRentalHomeAndHost(memberId, pageable);

        return reservations.map(ReservationResToGuestDto::new);
    }
}
