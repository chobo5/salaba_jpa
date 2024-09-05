package salaba.entity.member;

import org.junit.jupiter.api.Test;
import salaba.domain.common.entity.Address;
import salaba.domain.member.entity.Member;
import salaba.domain.member.constants.Gender;
import salaba.domain.common.entity.Nation;
import salaba.exception.ValidationException;
import salaba.domain.member.constants.MemberStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {

    @Test
    public void 회원생성_이메일유효성검사() {
        //given
        final String rightEmail = "test@test.com";

        List<String> wrongEmails = new ArrayList<>();
        for (int i = 4; i <= 10; i++) {
            wrongEmails.add(rightEmail.substring(0, i));
        }

        final String password = "";
        final String nickname = "";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when

        //then
        wrongEmails.forEach(email -> assertThatThrownBy(() -> Member.createMember(email, password, name, nickname, birthday))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("올바른 이메일 형식이 아닙니다."));

        assertThatThrownBy(() -> Member.createMember(rightEmail, password, name, nickname, birthday))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함한 8자 이상이여야 합니다.");
    }

    @Test
    public void 회원생성_비밀번호유효성검사() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        List<String> wrongPasswords = new ArrayList<>();
        for (int i = 1; i < password.length() - 1; i++) {
            wrongPasswords.add(password.substring(0, i));
        }


        //when

        //then
        wrongPasswords.forEach(pw -> assertThatThrownBy(() -> Member.createMember(email, pw, name, nickname, birthday))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함한 8자 이상이여야 합니다."));

        assertThatThrownBy(() -> Member.createMember(email, password, name, nickname, birthday))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("닉네임은 소문자, 대문자, 숫자, 언더스코어(_)만 포함하며, 4자에서 20자 사이의 길이여야 합니다.");
    }

    @Test
    public void 회원생성_닉네임유효성검사() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        List<String> wrongNicknames = new ArrayList<>();
        wrongNicknames.add("a");
        wrongNicknames.add("a_b");
        wrongNicknames.add("abc");
        wrongNicknames.add("abc!@");
        wrongNicknames.add("abcdefghijklmnopqrstuvwxyz");

        //when

        //then
        wrongNicknames.forEach(nick -> assertThatThrownBy(() -> Member.createMember(email, password, name, nick, birthday))
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("닉네임은 소문자, 대문자, 숫자, 언더스코어(_)만 포함하며, 4자에서 20자 사이의 길이여야 합니다."));

        Member member = Member.createMember(email, password, name, nickname, birthday);

        assertThat(member).isExactlyInstanceOf(Member.class);
    }

    @Test
    public void 회원_프로필변경() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.createMember(email, password, name, nickname, birthday);


        String changedName = "changedName";
        Gender gender = Gender.FEMALE;
        Nation nation = new Nation(82, "kor");
        Address address = new Address("teststreet", 123456);

        //when
        member.changeProfile(changedName, gender, nation, address);

        //then
        assertThat(member.getName()).isEqualTo(changedName);
        assertThat(member.getGender()).isEqualTo(gender);
        assertThat(member.getNation()).isEqualTo(nation);
        assertThat(member.getAddress()).isEqualTo(address);
    }

    @Test
    public void 회원탈퇴() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.createMember(email, password, name, nickname, birthday);

        //when
        member.resign();

        //then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.RESIGN);
    }

}