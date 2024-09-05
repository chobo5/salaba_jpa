package salaba.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.common.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
