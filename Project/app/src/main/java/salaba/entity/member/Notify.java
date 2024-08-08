package salaba.entity.member;

import lombok.Getter;
import salaba.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;


}
