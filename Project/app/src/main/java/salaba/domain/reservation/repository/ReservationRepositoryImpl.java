package salaba.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.domain.member.entity.QMember;
import salaba.domain.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.reservation.entity.QReservation.reservation;


@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Reservation> findWithGuest(Long hostId, Pageable pageable) {
        QMember host = new QMember("host");
        List<Reservation> reservations = queryFactory.selectFrom(reservation)
                .join(reservation.member, member).fetchJoin()
                .join(reservation.rentalHome, rentalHome).fetchJoin()
                .join(rentalHome.host, host).fetchJoin()
                .where(reservation.rentalHome.host.id.eq(hostId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(reservation.id.count())
                .from(reservation)
                .join(reservation.rentalHome, rentalHome)
                .join(rentalHome.host, host)
                .where(reservation.rentalHome.host.id.eq(hostId));

        return PageableExecutionUtils.getPage(reservations, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<Reservation> findWithRentalHomeAndHost(Long memberId, Pageable pageable) {
        List<Reservation> reservations = queryFactory.selectFrom(reservation)
                .join(reservation.rentalHome, rentalHome).fetchJoin()
                .join(rentalHome.host, member).fetchJoin()
                .where(reservation.member.id.eq(memberId))
                .orderBy(reservation.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(reservation.id.count())
                .from(reservation)
                .where(reservation.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(reservations, pageable, totalCount::fetchOne);
    }

    @Override
    public Optional<Reservation> findByIdWithMember(Long reservationId) {
        Reservation findReservation = queryFactory.selectFrom(reservation)
                .join(reservation.member, member).fetchJoin()
                .where(reservation.id.eq(reservationId))
                .fetchOne();
        return Optional.ofNullable(findReservation);
    }
}
