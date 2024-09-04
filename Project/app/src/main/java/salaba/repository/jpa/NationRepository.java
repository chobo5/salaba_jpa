package salaba.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.Nation;

@Repository
public interface NationRepository extends JpaRepository<Nation, Integer> {
}
