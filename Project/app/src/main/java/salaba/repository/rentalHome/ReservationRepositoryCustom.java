package salaba.repository.rentalHome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.entity.rental.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {
    Page<Reservation> findWithGuest(Long rentalHomeId, Pageable pageable);

    Page<Reservation> findWithRentalHomeAndHost(Long memberId, Pageable pageable);
}
