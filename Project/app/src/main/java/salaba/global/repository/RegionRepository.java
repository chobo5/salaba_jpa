package salaba.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.global.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
