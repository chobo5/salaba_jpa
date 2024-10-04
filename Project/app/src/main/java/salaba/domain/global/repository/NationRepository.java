package salaba.domain.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.global.entity.Nation;

import java.util.Optional;

@Repository
public interface NationRepository extends JpaRepository<Nation, Integer> {
    Optional<Nation> findByNameContaining(String name);
}
