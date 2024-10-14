package salaba.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.reservation.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
