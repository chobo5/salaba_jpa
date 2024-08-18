package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.RentalHomeTheme;
import salaba.entity.rental.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
