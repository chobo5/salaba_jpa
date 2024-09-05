package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.rentalHome.entity.RentalHomeTheme;

@Repository
public interface RentalHomeThemeRepository extends JpaRepository<RentalHomeTheme, Long> {
}
