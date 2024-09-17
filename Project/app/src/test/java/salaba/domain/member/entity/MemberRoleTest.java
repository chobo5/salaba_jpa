package salaba.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import salaba.domain.member.constants.RoleName;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRoleTest {

    @Test
    public void 회원_권한확인() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        Member member = Member.createMember(email, password, name, nickname, birthday);
        Role role = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        MemberRole memberRole = MemberRole.createMemberRole(member, role);

        //given
        MemberRole memberRoleFromMember = member.getRoles().stream().toList().get(0);

        //then
        assertThat(memberRoleFromMember.getRole()).isEqualTo(role);
        assertThat(memberRoleFromMember.getRole().getId()).isEqualTo(RoleName.MEMBER.getId());
        assertThat(memberRoleFromMember.getRole().getRoleName()).isEqualTo(RoleName.MEMBER);
        assertThat(memberRoleFromMember.getMember()).isEqualTo(member);
        assertThat(memberRoleFromMember).isEqualTo(memberRole);
    }
  
}