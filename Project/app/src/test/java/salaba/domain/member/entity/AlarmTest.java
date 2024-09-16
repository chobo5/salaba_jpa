package salaba.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AlarmTest {
    private Member member;
    private Alarm alarm;

    @BeforeEach
    public void 회원_알람생성() {
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        member = Member.createMember(email, password, name, nickname, birthday);

        alarm = Alarm.createReplyAlarm(member, "chobo6", "alarm content");
    }

    @Test
    public void 알람객체확인() {
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
        //when
        alarm.readAlarm();

        //then
        assertThat(alarm.getIsRead()).isEqualTo(true);
        assertThat(member.getAlarms().get(0).getIsRead()).isEqualTo(true);
    }


}