package salaba.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import salaba.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
