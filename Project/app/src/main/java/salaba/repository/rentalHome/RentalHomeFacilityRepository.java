package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.RentalHomeFacility;

@Repository
public interface RentalHomeFacilityRepository extends JpaRepository<RentalHomeFacility, Long> {
}
