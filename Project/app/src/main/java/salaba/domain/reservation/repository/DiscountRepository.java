package salaba.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.reservation.entity.Discount;
import salaba.domain.reservation.entity.Reservation;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    public void deleteByReservation(Reservation reservation);
}
