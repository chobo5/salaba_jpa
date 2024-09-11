package salaba.domain.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.MemberRole;
import salaba.domain.member.entity.Role;
import salaba.domain.member.constants.RoleName;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MemberRoleRepository memberRoleRepository;


    @Test
    public void 권한과회원찾기() {
        //given
        Member member = Member.createMember("test1@test.com", "Aa1234567!@", "testname1",
                "testNickName1", LocalDate.of(1996, 10, 8));
        memberRepository.save(member);

        Role role1 = roleRepository.findByRoleName(RoleName.MEMBER).get();
        Role role2 = roleRepository.findByRoleName(RoleName.MANAGER).get();
        Role role3 = roleRepository.findByRoleName(RoleName.ADMIN).get();

        MemberRole memberRole = MemberRole.createMemberRole(member, role1);
        memberRoleRepository.save(memberRole);
        MemberRole memberRole2 = MemberRole.createMemberRole(member, role2);
        memberRoleRepository.save(memberRole2);
        MemberRole memberRole3 = MemberRole.createMemberRole(member, role3);
        memberRoleRepository.save(memberRole3);

        //when
       Member findMember = memberRepository.findByEmail("test1@test.com").get();

        //then

        assertThat(findMember.getRoles().size()).isEqualTo(3);
        assertThat(findMember.getRoles()).contains(memberRole).contains(memberRole2).contains(memberRole3);

    }

}