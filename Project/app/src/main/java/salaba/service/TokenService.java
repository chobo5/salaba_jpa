package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.response.TokenResDto;
import salaba.entity.RefreshToken;
import salaba.entity.member.Member;
import salaba.repository.redis.RefreshTokenRedisRepository;
import salaba.security.jwt.util.JwtTokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public synchronized TokenResDto createTokens(Member member) { //여러 기기에서 로그인시 충돌 방지
        List<String> roles = member.getRoles().stream().map(mr -> mr.getRole().getRoleName().name())
                .collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는것이 더 좋다.
        Optional<RefreshToken> findRefreshToken = refreshTokenRedisRepository.findByMemberId(member.getId());

        if (findRefreshToken.isEmpty()) { //회원의 refreshToken이 없으면 새로 생성
            RefreshToken refreshTokenEntity = new RefreshToken(member.getId(), refreshToken, 60 * 60 * 24 * 7);
            refreshTokenRedisRepository.save(refreshTokenEntity);
        } else { //회원의 refreshToken이 있으면 갱신
            findRefreshToken.get().update(refreshToken, 60 * 60 * 24 * 7);
        }

        return new TokenResDto(accessToken, refreshToken);
    }

    public void deleteRefreshToken(String value) {
        refreshTokenRedisRepository.deleteByValue(value);
    }
}
