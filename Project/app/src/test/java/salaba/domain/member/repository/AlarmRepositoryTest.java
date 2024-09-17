package salaba.domain.member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.member.constants.RoleName;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.MemberRole;
import salaba.domain.member.entity.Role;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class AlarmRepositoryTest {
    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 알람생성하기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.createMember(email, password, name, nickname, birthday);
        em.persist(member);

        //when
        Alarm replyAlarm = Alarm.createReplyAlarm(member, "test", "content");
        alarmRepository.save(replyAlarm);

        //then
        assertThat(replyAlarm.getId()).isNotNull();
        assertThat(replyAlarm.getTargetMember()).isEqualTo(member);

    }

    @Test
    public void 회원의알람내역() {
        Member member = null;
        try {
            //given
            final String email = "test@test.com";
            final String password = "Tt12241509!@";
            final String name = "chobo";
            final String nickname = "chobo";
            final LocalDate birthday = LocalDate.of(1996, 10, 8);
            member = Member.createMember(email, password, name, nickname, birthday);
            em.persist(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Alarm replyAlarm = Alarm.createReplyAlarm(member, "test", "content");
        alarmRepository.save(replyAlarm);

        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<Alarm> alarms = alarmRepository.findByTargetMember(member, pageable);

        //then
        assertThat(alarms.getTotalElements()).isEqualTo(1);
        assertThat(alarms.getTotalPages()).isEqualTo(1);
        assertThat(alarms.getContent().get(0)).isEqualTo(replyAlarm);
        assertThat(alarms.getContent().get(0).getIsRead()).isFalse();

    }

    @Test
    public void 회원의알람읽기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.createMember(email, password, name, nickname, birthday);
        em.persist(member);

        Alarm replyAlarm = Alarm.createReplyAlarm(member, "test", "content");
        alarmRepository.save(replyAlarm);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Alarm> alarms = alarmRepository.findByTargetMember(member, pageable);
        alarms.getContent().get(0).readAlarm();
        em.flush();
        em.clear();

        //when
        Page<Alarm> alarmsAfterRead = alarmRepository.findByTargetMember(member, pageable);

        //then
        assertThat(alarmsAfterRead.getContent().get(0).getIsRead()).isTrue();

    }

}