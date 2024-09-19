package salaba.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token") //객체를 레디스에 저장할 때 Hash 자료구조를 사용
public class RefreshToken {
    @Id
    private Long memberId;

    @Indexed
    private String value;

    //@Id 또는 @Indexed 어노테이션을 적용한 프로퍼티들만 CrudRepository가 제공하는 findBy~ 구문을 사용할 수 있다.

    @TimeToLive
    private long ttl;

    public static RefreshToken create(Long memberId, String value) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.memberId = memberId;
        refreshToken.value = value;
        refreshToken.ttl = 60 * 60 * 24 * 7;
        return refreshToken;
    }

    public void update(String value) {
        this.value = value;
        this.ttl = 60 * 60 * 24 * 7;;
    }
}
