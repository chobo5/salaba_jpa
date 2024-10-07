package salaba.domain.member.repository.custom;

import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);

    void updateMemberWhereLastLoginDateIsBeforeAYear();
}
