package salaba.domain.member.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class AlarmTest {

    @Test
    public void 알람객체확인() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);

        Alarm alarm = Alarm.createReplyAlarm(member, "chobo6", "alarm content");

        //when
        Alarm memberAlarm = member.getAlarms().get(0);

        //then
        assertThat(memberAlarm.getId()).isNull();
        assertThat(memberAlarm.getTargetMember()).isEqualTo(member);
        assertThat(memberAlarm.getContent()).isEqualTo("chobo6" + "님이 댓글을 작성하였습니다. " + "alarm content");
        assertThat(memberAlarm).isEqualTo(alarm);
    }

    @Test
    public void 알람읽기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);

        Alarm alarm = Alarm.createReplyAlarm(member, "chobo6", "alarm content");

        //when
        alarm.readAlarm();

        //then
        assertThat(alarm.getIsRead()).isEqualTo(true);
        assertThat(member.getAlarms().get(0).getIsRead()).isEqualTo(true);
    }


}