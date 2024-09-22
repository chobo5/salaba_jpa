package salaba.domain.rentalHome.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import salaba.domain.common.constants.ProcessStatus;

import javax.transaction.Transactional;

import java.util.List;
import static salaba.domain.common.entity.QRegion.region;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.rentalHome.entity.QRentalHomeTheme.rentalHomeTheme;
import static salaba.domain.rentalHome.entity.QReview.review;
import static salaba.domain.rentalHome.entity.QTheme.theme;
import static salaba.domain.reservation.entity.QReservation.reservation;

@SpringBootTest
@Transactional
class RentalHomeRepositoryImplTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 숙소찾기_리뷰순() {
        //given
        String regionName = "seoul";
        String themeName = "beach";
        Long maxPrice = 55000L;
        Long minPrice = 50000L;
        Pageable pageable = PageRequest.of(0, 2000);

        //when
        List<Tuple> result = queryFactory.select(rentalHome, review.count(), review.score.avg())
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
                .leftJoin(review).on(review.reservation.id.eq(reservation.id))
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice),
                        reservation.status.eq(ProcessStatus.COMPLETE))
                .groupBy(rentalHome.id)
                .orderBy(review.score.avg().desc(), review.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Assertions.assertThat(result.size()).isEqualTo(1734);

    }

    @Test
    public void 숙소찾기_리뷰순_총개수() {
        //given
        String regionName = "seoul";
        String themeName = "beach";
        Long maxPrice = 55000L;
        Long minPrice = 50000L;
        Pageable pageable = PageRequest.of(0, 2000);

        //when
        Long totalCount = queryFactory.select(rentalHome.id.countDistinct())
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice),
                        reservation.status.eq(ProcessStatus.COMPLETE))
                .fetchOne();

        Assertions.assertThat(totalCount).isEqualTo(1734);
    }

    private BooleanExpression regionNameContains(String regionName) {
        return regionName != null ? region.name.contains(regionName) : null;
    }

    private BooleanExpression themeNameContains(String themeName) {
        return themeName != null ? theme.name.contains(themeName) : null;
    }

    private BooleanExpression setMinPrice(Long minPrice) {
        return minPrice != null ? rentalHome.price.goe(minPrice) : null;
    }

    private BooleanExpression setMaxPrice(Long maxPrice) {
        return maxPrice != null ? rentalHome.price.loe(maxPrice) : null;
    }

    @Test
    @Rollback(value = false)
    public void 리뷰평균벌크쿼리() {
        JPAQuery<Double> avg = queryFactory.select(review.score.avg().coalesce(0.0))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));


        queryFactory.update(rentalHome)
                .set(rentalHome.reviewAvg, avg)
                .execute();
    }

    @Test
    @Rollback(value = false)
    public void 리뷰합벌크쿼리() {
        JPAQuery<Long> sum = queryFactory.select(review.score.sum().longValue().coalesce(0L))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));


        queryFactory.update(rentalHome)
                .set(rentalHome.reviewSum, sum)
                .execute();
    }

    @Test
    @Rollback(value = false)
    public void 리뷰개수벌크쿼리() {
        JPAQuery<Long> count = queryFactory.select(review.score.count())
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));


        queryFactory.update(rentalHome)
                .set(rentalHome.reviewCount, count)
                .execute();
    }

}