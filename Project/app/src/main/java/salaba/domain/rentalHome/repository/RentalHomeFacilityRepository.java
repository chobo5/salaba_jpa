package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.rentalHome.entity.RentalHomeFacility;

@Repository
public interface RentalHomeFacilityRepository extends JpaRepository<RentalHomeFacility, Long> {
}
