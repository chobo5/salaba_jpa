package salaba.domain.member.entity;

import lombok.Getter;
import salaba.domain.common.entity.BaseEntity;

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

    public static Point createBoardPoint(Member member) {
        Point point = new Point();
        point.content = "게시글 작성";
        point.amount = 10;
        point.member = member;
        member.getPointHistories().add(point);
        return point;
    }

    public static Point createReplyPoint(Member member) {
        Point point = new Point();
        point.content = "댓글 작성";
        point.amount = 5;
        point.member = member;
        member.getPointHistories().add(point);
        return point;
    }

    public static Point createPaymentPoint(Member member, int price) {
        Point point = new Point();
        point.content = "숙소 이용";
        point.amount = (int) Math.ceil(price * 0.05);
        point.member = member;
        member.getPointHistories().add(point);
        return point;
    }

    public static Point createReviewPoint(Member member) {
        Point point = new Point();
        point.content = "숙소 리뷰 작성";
        point.amount = 100;
        point.member = member;
        member.getPointHistories().add(point);
        return point;
    }

    public static Point createUsedPoint(String content, int usedPoint, Member member) {
        int negativePoint = Math.abs(usedPoint) * -1;
        if (negativePoint != 0) {
            Point point = new Point();
            point.content = content;
            point.amount = usedPoint;
            point.member = member;
            member.getPointHistories().add(point);
            return point;
        }
        return null;
    }

    public void deletePointHistory(Point point) {
        member.getPointHistories().remove(point);
    }


}