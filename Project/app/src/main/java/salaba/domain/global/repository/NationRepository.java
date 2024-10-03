package salaba.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.global.entity.Nation;

@Repository
public interface NationRepository extends JpaRepository<Nation, Integer> {
}
