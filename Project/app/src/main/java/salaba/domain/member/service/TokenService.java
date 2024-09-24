package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.dto.response.TokenResDto;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.RefreshToken;
import salaba.security.jwt.util.JwtTokenizer;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, RefreshToken> redisTemplate;

    public synchronized TokenResDto createTokens(Member member) { //여러 기기에서 로그인시 충돌 방지
        List<String> roles = member.getRoles().stream().map(mr -> mr.getRole().getRoleName().name())
                .collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), roles);
        String refreshTokenKey = refreshTokenKeyMaker(member.getId());

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는것이 더 좋다.
        RefreshToken findRefreshToken = redisTemplate.opsForValue().get(refreshTokenKey);

        if (findRefreshToken == null) { //회원의 refreshToken이 없으면 새로 생성
            RefreshToken refreshTokenEntity = RefreshToken.create(member.getId(), refreshToken);
            redisTemplate.opsForValue().set(refreshTokenKey, refreshTokenEntity);
        } else { //회원의 refreshToken이 있으면 갱신
            findRefreshToken.update(refreshToken);
            redisTemplate.opsForValue().set(refreshTokenKey, findRefreshToken);
        }

        return new TokenResDto(accessToken, refreshToken);
    }

    public void deleteRefreshToken(Long memberId, String value) {
        String key = refreshTokenKeyMaker(memberId);
        RefreshToken refreshTokenEntity = redisTemplate.opsForValue().get(key);

        if (refreshTokenEntity != null && refreshTokenEntity.getValue().equals(value)) {
            redisTemplate.delete(refreshTokenKeyMaker(memberId));
        } else {
            throw new ValidationException("memberId와 refreshToken이 일치하지 않습니다.");
        }

    }

    private String refreshTokenKeyMaker(Long id) {
        return "refresh_token:" + id;
    }
}
