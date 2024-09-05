package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.rentalHome.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
