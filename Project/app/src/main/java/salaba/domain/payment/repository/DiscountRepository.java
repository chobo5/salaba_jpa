package salaba.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.payment.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
