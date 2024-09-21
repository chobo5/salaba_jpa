package salaba.config;

import org.springframework.data.domain.AuditorAware;
import salaba.interceptor.MemberContextHolder;

import java.util.Optional;

public class JwtAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String memberId = String.valueOf(MemberContextHolder.getMemberId());
        return Optional.of(memberId);
    }
}
