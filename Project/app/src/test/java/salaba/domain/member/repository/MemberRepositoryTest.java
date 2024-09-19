package salaba.domain.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.MemberRole;
import salaba.domain.member.entity.Role;
import salaba.domain.member.constants.RoleName;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 데이터베이스_연결_확인() {
        assertThat(memberRepository.count()).isEqualTo(0);
    }

    @Test
    public void 이메일로회원찾기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);
        memberRepository.save(member);
        Role role = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        em.persist(role);
        MemberRole memberRole = MemberRole.create(member, role);
        em.persist(memberRole);


        //when
        Optional<Member> findMember = memberRepository.findByEmail(email);

        //then
        assertThat(findMember).isNotEmpty();
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    public void 이메일_비밀번호로회원찾기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);


        Member member = Member.create(email, password, name, nickname, birthday);
        memberRepository.save(member);
        Role role = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        em.persist(role);
        MemberRole memberRole = MemberRole.create(member, role);
        em.persist(memberRole);

        //when
        Optional<Member> findMember = memberRepository.findByEmailAndPassword(email, password);

        //then
        assertThat(findMember.get()).isEqualTo(member);

    }

    @Test
    public void 닉네임으로회원찾기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);


        Member member = Member.create(email, password, name, nickname, birthday);
        memberRepository.save(member);
        Role role = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        em.persist(role);
        MemberRole memberRole = MemberRole.create(member, role);
        em.persist(memberRole);

        //when
        Optional<Member> findMember = memberRepository.findByNickname(nickname);

        //then
        assertThat(findMember.get()).isEqualTo(member);
    }

}