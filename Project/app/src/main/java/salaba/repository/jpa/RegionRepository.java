package salaba.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
