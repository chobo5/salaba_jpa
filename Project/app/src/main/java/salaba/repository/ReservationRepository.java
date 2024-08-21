package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
