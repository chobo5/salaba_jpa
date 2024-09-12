package salaba.domain.member.entity;

import org.junit.jupiter.api.Test;
import salaba.domain.common.entity.Address;
import salaba.domain.member.constants.Gender;
import salaba.domain.common.entity.Nation;
import salaba.domain.member.constants.MemberStatus;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {

    @Test
    public void 회원생성() {
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