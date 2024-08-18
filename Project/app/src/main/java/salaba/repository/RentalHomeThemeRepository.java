package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.RentalHomeTheme;

@Repository
public interface RentalHomeThemeRepository extends JpaRepository<RentalHomeTheme, Long> {
}
