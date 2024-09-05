package salaba.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.domain.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

import static salaba.entity.member.QMember.member;
import static salaba.entity.rental.QRentalHome.rentalHome;
import static salaba.entity.rental.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Reservation> findWithGuest(Long rentalHomeId, Pageable pageable) {
        List<Reservation> reservations = queryFactory.selectFrom(reservation)
                .join(reservation.member, member).fetchJoin()
                .where(reservation.rentalHome.id.eq(rentalHomeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(reservation.id.count())
                .from(reservation)
                .where(reservation.rentalHome.id.eq(rentalHomeId));

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
