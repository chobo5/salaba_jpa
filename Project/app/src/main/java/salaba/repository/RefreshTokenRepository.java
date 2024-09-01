package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import salaba.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
