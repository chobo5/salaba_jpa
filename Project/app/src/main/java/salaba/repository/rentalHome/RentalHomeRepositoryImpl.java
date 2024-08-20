package salaba.repository.rentalHome;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import salaba.dto.response.RentalHomeResDto;
import salaba.entity.member.QMember;
import salaba.entity.rental.*;

import java.util.List;
import java.util.Optional;
import static salaba.entity.QRegion.*;
import static salaba.entity.member.QMember.*;
import static salaba.entity.rental.QFacility.*;
import static salaba.entity.rental.QRentalHome.*;
import static salaba.entity.rental.QRentalHomeFacility.*;
import static salaba.entity.rental.QRentalHomeTheme.*;
import static salaba.entity.rental.QTheme.*;

@RequiredArgsConstructor
public class RentalHomeRepositoryImpl implements RentalHomeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public RentalHomeResDto get(Long rentalHomeId) {
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


        return new RentalHomeResDto(findRentalHome, findThemes, findFacilities);

    }
}
