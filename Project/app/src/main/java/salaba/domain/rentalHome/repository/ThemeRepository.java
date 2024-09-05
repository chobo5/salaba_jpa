package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.rentalHome.entity.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
