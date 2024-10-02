package salaba.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.global.constants.ProcessStatus;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.custom.ReservationRepositoryCustom;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    List<Reservation> findByRentalHomeAndStatus(RentalHome rentalHome, ProcessStatus status);
}
