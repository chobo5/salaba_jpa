package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.Region;

@Repository
public interface RegionRepsitory extends JpaRepository<Region, Long> {
}
