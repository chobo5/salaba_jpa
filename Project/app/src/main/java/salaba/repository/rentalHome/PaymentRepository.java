package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import salaba.entity.rental.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
