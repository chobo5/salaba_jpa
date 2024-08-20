package salaba.entity.member;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    public Alarm createAlarm(Member member, String content) {
        Alarm alarm = new Alarm();
        alarm.member = member;
        alarm.content = content;
        alarm.isRead = false;
        member.getAlarms().add(alarm);
        return alarm;
    }

    public void readAlarm() {
        this.isRead = true;
    }

}
