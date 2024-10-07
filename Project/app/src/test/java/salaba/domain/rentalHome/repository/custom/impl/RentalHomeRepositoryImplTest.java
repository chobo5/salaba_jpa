package salaba.domain.rentalHome.repository.custom.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import salaba.domain.global.constants.ProcessStatus;
import salaba.domain.rentalHome.entity.RentalHome;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.rentalHome.entity.QRentalHomeTheme.rentalHomeTheme;

import static salaba.domain.rentalHome.entity.QTheme.theme;
import static salaba.domain.reservation.entity.QReservation.reservation;
import static salaba.domain.reservation.entity.QReview.review;
import static salaba.domain.global.entity.QRegion.region;

@SpringBootTest
@Transactional
class RentalHomeRepositoryImplTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

//    @Test
//    public void 숙소찾기_리뷰순() {
//        //given
//        String regionName = "seoul";
//        String themeName = "beach";
//        Long maxPrice = 55000L;
//        Long minPrice = 50000L;
//        Pageable pageable = PageRequest.of(0, 2000);
//
//        //when
//        List<Tuple> result = queryFactory.select(rentalHome, review.count(), review.score.avg())
//                .from(rentalHome)
//                .join(rentalHome.region, region)
//                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
//                .leftJoin(review).on(review.reservation.id.eq(reservation.id))
//                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
//                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
//                .where(regionNameContains(regionName),
//                        themeNameContains(themeName),
//                        setMaxPrice(maxPrice),
//                        setMinPrice(minPrice),
//                        reservation.status.eq(ProcessStatus.COMPLETE))
//                .groupBy(rentalHome.id)
//                .orderBy(review.score.avg().desc(), review.count().desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        boolean rentalHomeLoaded = emf.getPersistenceUnitUtil().isLoaded(result.get(0).get(rentalHome));
//        boolean reservationsLoaded = emf.getPersistenceUnitUtil().isLoaded(result.get(0).get(rentalHome).getReservations());
//
//        assertThat(result.size()).isEqualTo(1734);
//        assertThat(rentalHomeLoaded).isTrue();
//        assertThat(reservationsLoaded).isTrue();
//
//    }
//
//    @Test
//    public void 숙소찾기_리뷰순_총개수() {
//        //given
//        String regionName = "seoul";
//        String themeName = "beach";
//        Long maxPrice = 55000L;
//        Long minPrice = 50000L;
//        Pageable pageable = PageRequest.of(0, 2000);
//
//        //when
//        Long totalCount = queryFactory.select(rentalHome.id.countDistinct())
//                .from(rentalHome)
//                .join(rentalHome.region, region)
//                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
//                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
//                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
//                .where(regionNameContains(regionName),
//                        themeNameContains(themeName),
//                        setMaxPrice(maxPrice),
//                        setMinPrice(minPrice),
//                        reservation.status.eq(ProcessStatus.COMPLETE))
//                .fetchOne();
//
//        assertThat(totalCount).isEqualTo(1734);
//    }
//
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


//    @Test
//    public void 숙소_예약_조인_querydsl() {
//        RentalHome rh = queryFactory.selectFrom(rentalHome)
//                .join(rentalHome.reservations, reservation)
//                .where(rentalHome.id.eq(6486L))
//                .fetchOne();
//
//        boolean rentalHomeLoaded = emf.getPersistenceUnitUtil().isLoaded(rh);
//        assertThat(rentalHomeLoaded).isTrue();
//
//        boolean reservationsLoaded2 = emf.getPersistenceUnitUtil().isLoaded(rh.getReservations());
//        assertThat(reservationsLoaded2).isFalse();
//    }
//
    @Test
    public void 숙소_예약_패치조인_querydsl() {
        RentalHome rh = queryFactory.selectFrom(rentalHome)
                .join(rentalHome.reservations).fetchJoin()
                .where(rentalHome.id.eq(6486L))
                .fetchOne();

        boolean rentalHomeLoaded = emf.getPersistenceUnitUtil().isLoaded(rh);
        assertThat(rentalHomeLoaded).isTrue();

        boolean reservationsLoaded2 = emf.getPersistenceUnitUtil().isLoaded(rh.getReservations());
        assertThat(reservationsLoaded2).isTrue();
    }
//
//    @Test
//    public void 숙소_예약_조인_jpql() {
//        String query = "select rh from RentalHome rh join rh.reservations where rh.id = 6486L";
//        RentalHome rentalHome = em.createQuery(query, RentalHome.class).getSingleResult();
//        boolean rentalHomeLoaded = emf.getPersistenceUnitUtil().isLoaded(rentalHome);
//        boolean reservationsLoaded = emf.getPersistenceUnitUtil().isLoaded(rentalHome.getReservations());
//
//        assertThat(rentalHomeLoaded).isTrue();
//        assertThat(reservationsLoaded).isFalse();
//    }
//
//    @Test
//    public void 숙소_예약_패치조인_jpql() {
//        String query = "select rh from RentalHome rh join fetch rh.reservations where rh.id = 6486L";
//        RentalHome rentalHome = em.createQuery(query, RentalHome.class).getSingleResult();
//        boolean rentalHomeLoaded = emf.getPersistenceUnitUtil().isLoaded(rentalHome);
//        boolean reservationsLoaded = emf.getPersistenceUnitUtil().isLoaded(rentalHome.getReservations());
//
//        assertThat(rentalHomeLoaded).isTrue();
//        assertThat(reservationsLoaded).isTrue();
//    }
//
//    @Test
//    public void a() {
//        String regionName = "seoul";
//        String themeName = "beach";
//        Long maxPrice = 55000L;
//        Long minPrice = 50000L;
//        Pageable pageable = PageRequest.of(0, 2000);
//
//        List<Tuple> result = queryFactory.select(rentalHome, review.count(), review.score.avg())
//                .from(rentalHome)
//                .join(rentalHome.region, region).fetchJoin()
//                .leftJoin(rentalHome.reservations, reservation)
//                .leftJoin(review).on(review.reservation.id.eq(reservation.id))
//                .leftJoin(rentalHome.rentalHomeThemes, rentalHomeTheme)
//                .leftJoin(rentalHomeTheme.theme, theme)
//                .where(regionNameContains(regionName),
//                        themeNameContains(themeName),
//                        setMaxPrice(maxPrice),
//                        setMinPrice(minPrice),
//                        reservation.status.eq(ProcessStatus.COMPLETE))
//                .groupBy(rentalHome.id)
//                .orderBy(review.score.avg().desc(), review.count().desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> totalCount = queryFactory.select(rentalHome.id.countDistinct())
//                .from(rentalHome)
//                .join(rentalHome.region, region)
//                .leftJoin(rentalHome.reservations, reservation)
//                .leftJoin(rentalHome.rentalHomeThemes, rentalHomeTheme)
//                .leftJoin(rentalHomeTheme.theme, theme)
//                .where(regionNameContains(regionName),
//                        themeNameContains(themeName),
//                        setMaxPrice(maxPrice),
//                        setMinPrice(minPrice),
//                        reservation.status.eq(ProcessStatus.COMPLETE));
//    }
//
//    @Test
//    public void 리뷰평균벌크쿼리() {
//        JPAQuery<Double> avg = queryFactory.select(review.score.avg().coalesce(0.0))
//                .from(review)
//                .join(review.reservation, reservation)
//                .where(reservation.rentalHome.eq(rentalHome));
//
//        queryFactory.update(rentalHome)
//                .set(rentalHome.reviewAvg, avg)
//                .execute();
//    }
//
//    @Test
//    public void 리뷰합벌크쿼리() {
//        JPAQuery<Long> sum = queryFactory.select(review.score.sum().longValue().coalesce(0L))
//                .from(review)
//                .join(review.reservation, reservation)
//                .where(reservation.rentalHome.eq(rentalHome));
//
//
//        queryFactory.update(rentalHome)
//                .set(rentalHome.reviewSum, sum)
//                .execute();
//    }
//
//    @Test
//    public void 리뷰개수벌크쿼리() {
//        JPAQuery<Long> count = queryFactory.select(review.score.count())
//                .from(review)
//                .join(review.reservation, reservation)
//                .where(reservation.rentalHome.eq(rentalHome));
//
//
//        queryFactory.update(rentalHome)
//                .set(rentalHome.reviewCount, count)
//                .execute();
//    }

}