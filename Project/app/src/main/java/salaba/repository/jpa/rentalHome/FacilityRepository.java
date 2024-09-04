package salaba.repository.jpa.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.Facility;
import salaba.entity.rental.RentalHomeFacility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
