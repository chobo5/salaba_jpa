package salaba.repository.rentalHome;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import salaba.dto.response.RentalHomeDetailResDto;
import salaba.entity.rental.*;

import java.util.List;
import java.util.Optional;

import static salaba.entity.QRegion.*;
import static salaba.entity.member.QMember.*;
import static salaba.entity.rental.QFacility.*;
import static salaba.entity.rental.QRentalHome.*;
import static salaba.entity.rental.QRentalHomeFacility.*;
import static salaba.entity.rental.QRentalHomeTheme.*;
import static salaba.entity.rental.QReservation.*;
import static salaba.entity.rental.QTheme.*;

@RequiredArgsConstructor
public class RentalHomeRepositoryImpl implements RentalHomeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public RentalHomeDetailResDto get(Long rentalHomeId) {
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
    public List<RentalHome> findByHost(Long hostId) {
        return queryFactory.selectFrom(rentalHome)
                .leftJoin(rentalHome.region, region).fetchJoin()
                .where(rentalHome.host.id.eq(hostId))
                .orderBy(rentalHome.createdDate.desc())
                .fetch();
    }

    @Override
    public Optional<RentalHome> findWithReservations(Long rentalHomeId) {
        RentalHome findRentalHome = queryFactory.selectFrom(rentalHome)
                .leftJoin(rentalHome.reservations, reservation).fetchJoin()
                .where(rentalHome.id.eq(rentalHomeId))
                .fetchOne();
        return Optional.ofNullable(findRentalHome);
    }
}
