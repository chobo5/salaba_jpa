package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;

import java.util.Optional;

@Repository
public interface RentalHomeRepository extends JpaRepository<RentalHome, Long>, RentalHomeRepositoryCustom {
    Optional<RentalHome> findByIdAndHost(Long rentalHomeId, Member host);
}
