package salaba.repository.jpa.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;

import java.util.Optional;

@Repository
public interface RentalHomeRepository extends JpaRepository<RentalHome, Long>, RentalHomeRepositoryCustom {
    Optional<RentalHome> findByIdAndHost(Long rentalHomeId, Member host);
}
