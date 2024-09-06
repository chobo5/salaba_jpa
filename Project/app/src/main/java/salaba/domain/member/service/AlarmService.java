package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.AlarmRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public void createReplyAlarm(Member to, Member from, String content) {
        Alarm alarm = Alarm.createReplyAlarm(to, from.getNickname(), content);
        alarmRepository.save(alarm);
    }

    public Page<Alarm> getAlarmsToMember(Member member, Pageable pageable) {
        return alarmRepository.findByTargetMember(member, pageable);
    }
}
