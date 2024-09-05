package salaba.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import salaba.domain.member.entity.Member;

import java.util.Optional;

import static salaba.entity.member.QMember.member;
import static salaba.entity.member.QMemberRole.*;
import static salaba.entity.member.QRole.*;

@RequiredArgsConstructor
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
