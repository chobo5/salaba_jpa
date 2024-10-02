package salaba.domain.member.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.custom.MemberRepositoryCustom;

import java.util.Optional;

import static salaba.domain.auth.entity.QMemberRole.memberRole;
import static salaba.domain.auth.entity.QRole.role;
import static salaba.domain.member.entity.QMember.member;


@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Member> findByEmail(String email) {
        Member findMembers = queryFactory.select(member)
                .from(member)
                .leftJoin(member.roles, memberRole).fetchJoin()
                .join(memberRole.role, role).fetchJoin()
                .where(member.email.eq(email))
                .fetchFirst();
        return Optional.ofNullable(findMembers);
    }
}
