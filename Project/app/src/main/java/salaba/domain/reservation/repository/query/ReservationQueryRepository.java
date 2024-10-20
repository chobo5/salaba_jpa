package salaba.domain.reservation.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.QMember;
import salaba.domain.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.rentalHome.entity.QRentalHome.rentalHome;
import static salaba.domain.reservation.entity.QReservation.reservation;


@RequiredArgsConstructor
@Repository
public class ReservationQueryRepository {
    private final JPAQueryFactory queryFactory;

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

    
    public Optional<Reservation> findByIdWithMemberAndRentalHome(Long reservationId) {
        Reservation findReservation = queryFactory.selectFrom(reservation)
                .join(reservation.member, member).fetchJoin()
                .join(reservation.rentalHome, rentalHome).fetchJoin()
                .where(reservation.id.eq(reservationId))
                .fetchOne();
        return Optional.ofNullable(findReservation);
    }
}
