package salaba.entity.member;

import lombok.Getter;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class Point extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Point createPoint(String content, int amount, Member member) {
        if (amount > 0) {
            Point point = new Point();
            point.content = content;
            point.amount = amount;
            point.member = member;
            member.getPointHistories().add(point);
            return point;
        }
        return null;
    }

    public Point usePoint(String content, int usedPoint, Member member) {
        if (usedPoint < 0) {
            Point point = new Point();
            point.content = content;
            point.amount = usedPoint;
            point.member = member;
            member.getPointHistories().add(point);
            return point;
        }
        return null;
    }

}
