package salaba.domain.reservation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.domain.reservation.entity.Reservation;

import java.util.Optional;

public interface ReservationRepositoryCustom {
    Page<Reservation> findWithGuest(Long rentalHomeId, Pageable pageable);

    Page<Reservation> findWithRentalHomeAndHost(Long memberId, Pageable pageable);

    Optional<Reservation> findByIdWithMember(Long reservationId);
}
