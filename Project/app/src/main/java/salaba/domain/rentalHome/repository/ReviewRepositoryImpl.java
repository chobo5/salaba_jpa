package salaba.domain.rentalHome.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Review;

import java.util.List;
import java.util.Optional;

import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.rentalHome.entity.QReview.review;
import static salaba.domain.reservation.entity.QReservation.reservation;


@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Review> findByMember(Member member, Pageable pageable) {
        List<Review> reviews = queryFactory.select(review)
                .from(review)
                .join(review.reservation, reservation).fetchJoin()
                .where(review.reservation.member.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> reviewCount = queryFactory.select(review.count())
                .from(review)
                .join(review.reservation, reservation).fetchJoin()
                .where(review.reservation.member.eq(member));

        return PageableExecutionUtils.getPage(reviews, pageable, reviewCount::fetchOne);
    }

    @Override
    public Page<Review> findByRentalHome(RentalHome targetRentalHome, Pageable pageable) {
        List<Review> reviews = queryFactory.select(review)
                .from(review)
                .join(review.reservation, reservation)
                .join(reservation.rentalHome, rentalHome)
                .where(review.reservation.rentalHome.eq(targetRentalHome))
                .orderBy(review.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> reviewCount = queryFactory.select(review.count())
                .from(review)
                .join(review.reservation, reservation)
                .where(review.reservation.rentalHome.eq(targetRentalHome));

        return PageableExecutionUtils.getPage(reviews, pageable, reviewCount::fetchOne);
    }

    @Override
    public Optional<Review> findByIdWithReservationAndMemberAndRentalHome(Long reviewId) {
        Review findReview = queryFactory.select(review)
                .from(review)
                .join(review.reservation, reservation).fetchJoin()
                .join(reservation.member, member).fetchJoin()
                .join(reservation.rentalHome, rentalHome).fetchJoin()
                .where(review.id.eq(reviewId))
                .fetchOne();

        return Optional.of(findReview);
    }

    @Override
    public Double getReviewAvg(RentalHome rentalHome) {
        SimpleTemplate<Double> avg = Expressions.template(Double.class, "ROUND({0}, 2)", review.score.avg().coalesce(0.0));
        return queryFactory.select(avg)
                .from(review)
                .join(review.reservation, reservation)
                .where(review.reservation.rentalHome.eq(rentalHome))
                .fetchOne();
    }
}
