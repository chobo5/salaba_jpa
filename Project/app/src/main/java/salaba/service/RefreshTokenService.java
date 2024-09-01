package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.entity.RefreshToken;
import salaba.entity.member.Member;
import salaba.repository.RefreshTokenRepository;
import salaba.security.jwt.util.JwtTokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    public Map<String, String> createTokens(Member member) {
        List<String> roles = member.getRoles().stream().map(mr -> mr.getRole().getRoleName().name())
                .collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는것이 더 좋다.
        RefreshToken refreshTokenEntity = new RefreshToken(member.getId(), member, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }
}
