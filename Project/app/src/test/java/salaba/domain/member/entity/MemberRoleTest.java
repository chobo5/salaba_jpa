package salaba.domain.member.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salaba.domain.member.constants.RoleName;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
class MemberRoleTest {

    private Member member;
    private Role role;
    private MemberRole memberRole;

    @BeforeEach
    public void 권한생성() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        member = Member.createMember(email, password, name, nickname, birthday);
        role = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        memberRole = MemberRole.createMemberRole(member, role);
    }

    @Test
    public void 회원_권한확인() {
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