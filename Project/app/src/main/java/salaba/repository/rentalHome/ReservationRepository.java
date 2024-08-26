package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.ProcessStatus;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRentalHomeAndStatus(RentalHome rentalHome, ProcessStatus status);

}