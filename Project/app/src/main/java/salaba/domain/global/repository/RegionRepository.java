package salaba.domain.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.entity.Region;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByNation(Nation nation);

    Optional<Region> findByNameContaining(String name);
}
