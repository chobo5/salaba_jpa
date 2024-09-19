package salaba.domain.member.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import salaba.domain.common.entity.Address;
import salaba.domain.member.constants.Gender;
import salaba.domain.member.constants.Grade;
import salaba.domain.member.constants.MemberStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberTest {

    private Member member;

    @BeforeEach
    public void 회원생성() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        member = Member.create(email, password, name, nickname, birthday);
    }

    @Test
    public void 회원생성_검증() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //then
        assertThat(member.getId()).isNull();
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getBirthday()).isEqualTo(birthday);
        assertThat(member.getAddress()).isNull();
        assertThat(member.getTelNo()).isNull();
        assertThat(member.getWarningCount()).isEqualTo(0);
        assertThat(member.getExitDate()).isNull();
        assertThat(member.getLastLoginDate()).isInstanceOf(LocalDateTime.class);
        assertThat(member.getCreatedDate()).isNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
        assertThat(member.getGender()).isNull();
        assertThat(member.getGrade()).isEqualTo(Grade.BRONZE);
        assertThat(member.getNation()).isNull();
        assertThat(member.getBoards()).isEmpty();
        assertThat(member.getReplies()).isEmpty();
        assertThat(member.getReservations()).isEmpty();
        assertThat(member.getAlarms()).isEmpty();
        assertThat(member.getPointHistories()).isEmpty();
        assertThat(member.getBookMarks()).isEmpty();
        assertThat(member.getRoles()).isEmpty();
    }

    @Nested
    @DisplayName("회원정보변경")
    class ChangeInfo {
        @Test
        public void 회원_프로필변경() {
            //given
            final String changedName = "chobo5_modified";
            final Gender gender = Gender.FEMALE;
            final Address address = new Address("teststreet", 123456);

            //when
            member.changeProfile(changedName, gender, null, address);

            //then
            assertThat(member.getName()).isEqualTo(changedName);
            assertThat(member.getGender()).isEqualTo(gender);
            assertThat(member.getNation()).isNull();
            assertThat(member.getAddress()).isEqualTo(address);
        }

        @Test
        public void 이메일변경() {
            //given
            final String changedEmail = "chobo5@test.com";

            //when
            member.changeEmail(changedEmail);

            //then
            assertThat(member.getEmail()).isEqualTo(changedEmail);
        }

        @Test
        public void 비밀번호변경() {
            final String changedPassword = "Aa123456@";

            //when
            member.changePassword(changedPassword);

            //then
            assertThat(member.getPassword()).isEqualTo(changedPassword);

        }

        @Test
        public void 전화번호변경() {
            //given
            final String changedTelNo = "01022857358";

            //when
            member.changeTelNo(changedTelNo);

            //then
            assertThat(member.getTelNo()).isEqualTo(changedTelNo);
        }

        @Test
        public void 닉네임변경() {
            //given
            final String changedNickname = "chobo5_modified";

            //when
            member.changeNickname(changedNickname);

            //then
            assertThat(member.getNickname()).isEqualTo(changedNickname);
        }

    }



    @Test
    public void 회원로그인() {
        //when
        member.login();

        //then
        assertThat(member.getLastLoginDate()).isInstanceOf(LocalDateTime.class);
    }

    @Test
    public void 회원탈퇴() {
        //when
        member.resign();

        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.RESIGN);
        assertThat(member.getExitDate()).isInstanceOf(LocalDateTime.class);
    }

}