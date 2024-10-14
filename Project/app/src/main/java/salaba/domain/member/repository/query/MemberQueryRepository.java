package salaba.domain.member.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import salaba.domain.auth.constant.MemberStatus;
import salaba.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Optional;

import static salaba.domain.auth.entity.QMemberRole.memberRole;
import static salaba.domain.auth.entity.QRole.role;
import static salaba.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Member> findByEmail(String email) {
        Member findMembers = queryFactory.select(member)
                .from(member)
                .leftJoin(member.roles, memberRole).fetchJoin()
                .join(memberRole.role, role).fetchJoin()
                .where(member.email.eq(email))
                .fetchFirst();
        return Optional.ofNullable(findMembers);
    }

    public void updateMemberWhereLastLoginDateIsBeforeAYear() {
        queryFactory.update(member)
                .set(member.status, MemberStatus.SLEEP)
                .where(member.lastLoginDate.before(LocalDateTime.now().minusYears(1)));
    }
}


