package salaba.repository.rentalHome;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Review;

import java.util.List;

import static salaba.entity.rental.QReservation.*;
import static salaba.entity.rental.QReview.*;

@RequiredArgsConstructor
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
    public Page<Review> findByRentalHome(RentalHome rentalHome, Pageable pageable) {
        List<Review> reviews = queryFactory.select(review)
                .from(review)
                .join(review.reservation, reservation).fetchJoin()
                .where(review.reservation.rentalHome.eq(rentalHome))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> reviewCount = queryFactory.select(review.count())
                .from(review)
                .join(review.reservation, reservation).fetchJoin()
                .where(review.reservation.rentalHome.eq(rentalHome));

        return PageableExecutionUtils.getPage(reviews, pageable, reviewCount::fetchOne);
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
