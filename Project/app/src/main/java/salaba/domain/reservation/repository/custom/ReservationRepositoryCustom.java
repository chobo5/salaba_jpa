package salaba.domain.reservation.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import salaba.domain.reservation.entity.Reservation;

import java.util.Optional;

@Repository
public interface ReservationRepositoryCustom {
    Page<Reservation> findWithGuest(Long rentalHomeId, Pageable pageable);

    Page<Reservation> findWithRentalHomeAndHost(Long memberId, Pageable pageable);

    Optional<Reservation> findByIdWithMemberAndRentalHome(Long reservationId);
}
