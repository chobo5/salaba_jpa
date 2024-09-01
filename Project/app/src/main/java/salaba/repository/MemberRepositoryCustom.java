package salaba.repository;

import org.springframework.data.jpa.repository.Query;
import salaba.entity.member.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
}
