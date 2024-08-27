package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
