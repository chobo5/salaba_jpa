package salaba.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.common.entity.Nation;

@Repository
public interface NationRepository extends JpaRepository<Nation, Integer> {
}
