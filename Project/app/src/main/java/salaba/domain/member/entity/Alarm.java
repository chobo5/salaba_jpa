package salaba.domain.member.entity;

import lombok.Getter;
import salaba.domain.global.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class Alarm extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member targetMember;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    public static Alarm createReplyAlarm(Member targetMember, String writer, String content) { //댓글, 대댓글,
        Alarm alarm = new Alarm();
        alarm.targetMember = targetMember;
        alarm.content = writer + "님이 댓글을 작성하였습니다. " + content;
        alarm.isRead = false;
        return alarm;
    }

    public void readAlarm() {
        this.isRead = true;
    }

}
