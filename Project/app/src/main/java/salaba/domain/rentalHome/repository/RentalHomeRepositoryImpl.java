package salaba.domain.rentalHome.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.domain.rentalHome.entity.Facility;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Theme;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;

import java.util.List;
import java.util.Optional;

import static salaba.domain.common.entity.QRegion.region;
import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.rentalHome.entity.QFacility.facility;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.rentalHome.entity.QRentalHomeFacility.rentalHomeFacility;
import static salaba.domain.rentalHome.entity.QRentalHomeTheme.rentalHomeTheme;
import static salaba.domain.rentalHome.entity.QTheme.theme;
import static salaba.domain.reservation.entity.QReservation.reservation;


@RequiredArgsConstructor
public class RentalHomeRepositoryImpl implements RentalHomeRepositoryCustom{

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
}
