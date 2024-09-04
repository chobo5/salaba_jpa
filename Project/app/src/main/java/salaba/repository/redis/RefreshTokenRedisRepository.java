package salaba.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByValue(String value);

    void deleteByValue(String value);
}
