package salaba.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import salaba.entity.member.Member;
import salaba.entity.member.QMember;
import salaba.entity.member.QMemberRole;
import salaba.entity.member.QRole;

import java.util.List;
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
