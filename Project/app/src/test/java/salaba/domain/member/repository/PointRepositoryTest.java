package salaba.domain.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class PointRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    PointRepository pointRepository;

    @Test
    public void 포인트적립생성하기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.create(email, password, name, nickname, birthday);
        em.persist(member);

        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);
        Point usedPoint = Point.createUsedPoint("포인트 사용", 10000, member);

        //when
        Point savedBoardPoint = pointRepository.save(boardPoint);
        Point savedReplyPoint = pointRepository.save(replyPoint);
        Point savedPaymentPoint = pointRepository.save(paymentPoint);
        Point savedReviewPoint = pointRepository.save(reviewPoint);
        Point savedUsedPoint = pointRepository.save(usedPoint);

        //then
        assertThat(boardPoint.getId()).isNotNull();
        assertThat(replyPoint.getId()).isNotNull();
        assertThat(paymentPoint.getId()).isNotNull();
        assertThat(reviewPoint.getId()).isNotNull();
        assertThat(usedPoint.getId()).isNotNull();

        assertThat(savedBoardPoint).isEqualTo(boardPoint);
        assertThat(savedReplyPoint).isEqualTo(replyPoint);
        assertThat(savedPaymentPoint).isEqualTo(paymentPoint);
        assertThat(savedReviewPoint).isEqualTo(reviewPoint);
        assertThat(savedUsedPoint).isEqualTo(usedPoint);

    }

    @Test
    public void 회원의포인트내역가져오기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.create(email, password, name, nickname, birthday);
        em.persist(member);

        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);
        Point usedPoint = Point.createUsedPoint("포인트 사용", 10000, member);

        //when
        pointRepository.save(boardPoint);
        pointRepository.save(replyPoint);
        pointRepository.save(paymentPoint);
        pointRepository.save(reviewPoint);
        pointRepository.save(usedPoint);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Point> points = pointRepository.findByMember(member, pageable);

        //then
        assertThat(points.getTotalElements()).isEqualTo(5);
        assertThat(points.getTotalPages()).isEqualTo(1);
        assertThat(points.getContent()).contains(boardPoint);
        assertThat(points.getContent()).contains(replyPoint);
        assertThat(points.getContent()).contains(paymentPoint);
        assertThat(points.getContent()).contains(reviewPoint);
        assertThat(points.getContent()).contains(usedPoint);
    }

    @Test
    public void 회원의포인트총액가져오기() {
        //given
        final String email = "test@test.com";
        final String password = "Tt12241509!@";
        final String name = "chobo";
        final String nickname = "chobo";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);
        Member member = Member.create(email, password, name, nickname, birthday);
        em.persist(member);

        Point boardPoint = Point.createBoardPoint(member);
        Point replyPoint = Point.createReplyPoint(member);
        Point paymentPoint = Point.createPaymentPoint(member, 100000);
        Point reviewPoint = Point.createReviewPoint(member);
        Point usedPoint = Point.createUsedPoint("포인트 사용", 10000, member);

        pointRepository.save(boardPoint);
        pointRepository.save(replyPoint);
        pointRepository.save(paymentPoint);
        pointRepository.save(reviewPoint);
        pointRepository.save(usedPoint);

        //when
        int totalPoint = pointRepository.getTotalPoint(member.getId());

        //then
        assertThat(totalPoint).isEqualTo(-8885);

    }


}