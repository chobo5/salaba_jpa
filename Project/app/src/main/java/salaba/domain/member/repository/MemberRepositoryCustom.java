package salaba.domain.member.repository;

import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
}
