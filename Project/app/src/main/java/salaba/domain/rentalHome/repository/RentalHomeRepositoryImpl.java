package salaba.domain.rentalHome.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import salaba.domain.common.constants.ProcessStatus;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.Facility;
import salaba.domain.rentalHome.entity.QReview;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Theme;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static salaba.domain.common.entity.QRegion.region;
import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.rentalHome.entity.QFacility.facility;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.rentalHome.entity.QRentalHomeFacility.rentalHomeFacility;
import static salaba.domain.rentalHome.entity.QRentalHomeTheme.rentalHomeTheme;
import static salaba.domain.rentalHome.entity.QReview.review;
import static salaba.domain.rentalHome.entity.QTheme.theme;
import static salaba.domain.reservation.entity.QReservation.reservation;


@RequiredArgsConstructor
@Repository
public class RentalHomeRepositoryImpl implements RentalHomeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public RentalHomeDetailResDto findDetailById(Long rentalHomeId) {
        RentalHome findRentalHome = queryFactory.select(rentalHome)
                .from(rentalHome)
                .join(rentalHome.host, member).fetchJoin()
                .join(rentalHome.region, region).fetchJoin()
                .where(rentalHome.id.eq(rentalHomeId))
                .fetchOne();


        List<Facility> findFacilities = queryFactory.select(facility)
                .from(rentalHomeFacility)
                .join(rentalHomeFacility.facility, facility)
                .where(rentalHomeFacility.rentalHome.id.eq(rentalHomeId))
                .fetch();

        List<Theme> findThemes = queryFactory.select(theme)
                .from(rentalHomeTheme)
                .join(rentalHomeTheme.theme, theme)
                .where(rentalHomeTheme.rentalHome.id.eq(rentalHomeId))
                .fetch();


        return new RentalHomeDetailResDto(findRentalHome, findThemes, findFacilities);
    }

    @Override
    public RentalHomeDetailResDto findDetailByIdAndHost(Long rentalHomeId, Long hostId) {
        RentalHome findRentalHome = queryFactory.select(rentalHome)
                .from(rentalHome)
                .join(rentalHome.host, member).fetchJoin()
                .join(rentalHome.region, region).fetchJoin()
                .where(rentalHome.id.eq(rentalHomeId).and(rentalHome.host.id.eq(hostId)))
                .fetchOne();


        List<Facility> findFacilities = queryFactory.select(facility)
                .from(rentalHomeFacility)
                .join(rentalHomeFacility.facility, facility)
                .where(rentalHomeFacility.rentalHome.id.eq(rentalHomeId))
                .fetch();

        List<Theme> findThemes = queryFactory.select(theme)
                .from(rentalHomeTheme)
                .join(rentalHomeTheme.theme, theme)
                .where(rentalHomeTheme.rentalHome.id.eq(rentalHomeId))
                .fetch();


        return new RentalHomeDetailResDto(findRentalHome, findThemes, findFacilities);

    }


    @Override
    public Page<RentalHome> findByHost(Long hostId, Pageable pageable) {
        List<RentalHome> rentalHomes = queryFactory.selectFrom(rentalHome)
                .join(rentalHome.host, member).fetchJoin()
                .leftJoin(rentalHome.region, region).fetchJoin()
                .where(rentalHome.host.id.eq(hostId))
                .orderBy(rentalHome.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(rentalHome.id.count())
                .where(rentalHome.host.id.eq(hostId));

        return PageableExecutionUtils.getPage(rentalHomes, pageable, totalCount::fetchOne);
    }

    @Override
    public Optional<RentalHome> findWithReservations(Long rentalHomeId) {
        RentalHome findRentalHome = queryFactory.selectFrom(rentalHome)
                .join(rentalHome.host, member).fetchJoin()
                .leftJoin(rentalHome.reservations, reservation).fetchJoin()
                .where(rentalHome.id.eq(rentalHomeId))
                .fetchOne();
        return Optional.ofNullable(findRentalHome);
    }

    @Override
    public Page<RentalHome> findRentalHomesOrderByReview(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {
        List<RentalHome> result = queryFactory.select(rentalHome)
                .from(rentalHome)
                .join(rentalHome.region, region).fetchJoin()
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice))
                .groupBy(rentalHome.id)
                .orderBy(rentalHome.reviewAvg.desc(), rentalHome.reviewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(rentalHome.id.countDistinct())
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice));


        return PageableExecutionUtils.getPage(result, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<RentalHomeResDto> findRentalHomeDtosOrderByReview(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {

        List<RentalHomeResDto> rentalHomes = queryFactory.select(Projections.constructor(RentalHomeResDto.class,
                        rentalHome.id,
                        rentalHome.name,
                        rentalHome.address,
                        rentalHome.capacity,
                        rentalHome.cleanFee,
                        rentalHome.price,
                        rentalHome.region.name,
                        rentalHome.status,
                        rentalHome.reviewCount,
                        rentalHome.reviewAvg))
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice))
                .groupBy(rentalHome.id)
                .orderBy(rentalHome.reviewAvg.desc(), rentalHome.reviewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(rentalHome.id.countDistinct())
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice));
        return PageableExecutionUtils.getPage(rentalHomes, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<RentalHome> findRentalHomesOrderBySalesCount(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {
        List<Tuple> result = queryFactory.select(rentalHome, reservation.count())
                .from(rentalHome)
                .leftJoin(rentalHome.region, region).fetchJoin()
                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice),
                        reservation.status.eq(ProcessStatus.COMPLETE))
                .groupBy(rentalHome.id)
                .orderBy(reservation.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(rentalHome.id.countDistinct())
                .from(rentalHome)
                .join(rentalHome.region, region)
                .leftJoin(rentalHomeTheme).on(rentalHomeTheme.rentalHome.id.eq(rentalHome.id))
                .leftJoin(reservation).on(reservation.rentalHome.id.eq(rentalHome.id))
                .leftJoin(theme).on(rentalHomeTheme.theme.id.eq(theme.id))
                .where(regionNameContains(regionName),
                        themeNameContains(themeName),
                        setMaxPrice(maxPrice),
                        setMinPrice(minPrice),
                        reservation.status.eq(ProcessStatus.COMPLETE));

        List<RentalHome> rentalHomes = result.stream().map(tuple -> {
            RentalHome rh = tuple.get(rentalHome);
            rh.setSalesCount(tuple.get(reservation.count()));
            return rh;
        }).collect(Collectors.toList());

        return PageableExecutionUtils.getPage(rentalHomes, pageable, totalCount::fetchOne);
    }

    @Override
    public void updateReviewStatistics() {
        JPAQuery<Double> avg = queryFactory.select(review.score.avg().coalesce(0.0))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        JPAQuery<Long> sum = queryFactory.select(review.score.sum().longValue().coalesce(0L))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        JPAQuery<Long> count = queryFactory.select(review.score.count())
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));


        queryFactory.update(rentalHome)
                .set(rentalHome.reviewAvg, avg)
                .set(rentalHome.reviewSum, sum)
                .set(rentalHome.reviewCount, count)
                .execute();
    }

    @Override
    public void updateAReviewStatistics(RentalHome targetRentalHome) {
        JPAQuery<Double> avg = queryFactory.select(review.score.avg().coalesce(0.0))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        JPAQuery<Long> sum = queryFactory.select(review.score.sum().longValue().coalesce(0L))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        JPAQuery<Long> count = queryFactory.select(review.score.count())
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));


        queryFactory.update(rentalHome)
                .set(rentalHome.reviewAvg, avg)
                .set(rentalHome.reviewSum, sum)
                .set(rentalHome.reviewCount, count)
                .where(rentalHome.eq(targetRentalHome))
                .execute();
    }

    @Override
    public void updateAReviewAvgAndSum(RentalHome targetRentalHome) {
        JPAQuery<Double> avg = queryFactory.select(review.score.avg().coalesce(0.0))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        JPAQuery<Long> sum = queryFactory.select(review.score.sum().longValue().coalesce(0L))
                .from(review)
                .join(review.reservation, reservation)
                .where(reservation.rentalHome.eq(rentalHome));

        queryFactory.update(rentalHome)
                .set(rentalHome.reviewAvg, avg)
                .set(rentalHome.reviewSum, sum)
                .where(rentalHome.eq(targetRentalHome))
                .execute();
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

}
