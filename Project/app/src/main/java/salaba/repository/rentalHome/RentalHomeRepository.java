package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;

import java.util.List;

@Repository
public interface RentalHomeRepository extends JpaRepository<RentalHome, Long>, RentalHomeRepositoryCustom {

}
