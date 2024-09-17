package salaba.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salaba.exception.CannotBeZeroException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PointTest {

    @Test
    public void 포인트액수확인() {

        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        Member member = Member.createMember(email, password, name, nickname, birthday);
        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);

        //then
        assertThat(boardPoint.getAmount()).isEqualTo(10);
        assertThat(replyPoint.getAmount()).isEqualTo(5);
        assertThat(paymentPoint.getAmount()).isEqualTo(1000);
        assertThat(reviewPoint.getAmount()).isEqualTo(100);

    }

    @Test
    public void 포인트확인() {

        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        Member member = Member.createMember(email, password, name, nickname, birthday);
        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);
        List<Point> pointHistories = member.getPointHistories();

        //then
        assertThat(pointHistories.size()).isEqualTo(4);
        assertThat(pointHistories).contains(boardPoint);
        assertThat(pointHistories).contains(replyPoint);
        assertThat(pointHistories).contains(reviewPoint);
        assertThat(pointHistories).contains(paymentPoint);
    }

    @Test
    public void 포인트0원사용() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.createMember(email, password, name, nickname, birthday);

        assertThrows(CannotBeZeroException.class, () -> Point.createUsedPoint("포인트 사용", 0, member));

    }

    @Test
    public void 포인트적립취소() {
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo5";
        final String nickname = "chobo5";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        //when
        Member member = Member.createMember(email, password, name, nickname, birthday);
        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);

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