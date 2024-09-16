package salaba.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class PointTest {

    private Member member;
    private Point boardPoint;
    private Point replyPoint;
    private Point paymentPoint;
    private Point reviewPoint;

    @BeforeEach
    public void 포인트생성() {
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        member = Member.createMember(email, password, name, nickname, birthday);
        boardPoint = Point.createBoardPoint(member);
        replyPoint = Point.createReplyPoint(member);
        paymentPoint = Point.createPaymentPoint(member, 100000);
        reviewPoint = Point.createReviewPoint(member);
    }

    @Test
    public void 포인트액수확인() {
        //then
        assertThat(boardPoint.getAmount()).isEqualTo(10);
        assertThat(replyPoint.getAmount()).isEqualTo(5);
        assertThat(paymentPoint.getAmount()).isEqualTo(1000);
        assertThat(reviewPoint.getAmount()).isEqualTo(100);

    }

    @Test
    public void 포인트확인() {
        //given
        List<Point> pointHistories = member.getPointHistories();

        //then
        assertThat(pointHistories.size()).isEqualTo(4);
        assertThat(pointHistories).contains(boardPoint);
        assertThat(pointHistories).contains(replyPoint);
        assertThat(pointHistories).contains(reviewPoint);
        assertThat(pointHistories).contains(paymentPoint);
    }

    @Test
    public void 포인트적립취소() {
        //given
        List<Point> pointHistories = member.getPointHistories();

        //when
        paymentPoint.deletePointHistory();

        assertThat(pointHistories.size()).isEqualTo(3);
        assertThat(pointHistories).contains(boardPoint);
        assertThat(pointHistories).contains(replyPoint);
        assertThat(pointHistories).contains(reviewPoint);
        assertThat(pointHistories).doesNotContain(paymentPoint);

    }

}