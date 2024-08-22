package salaba.repository.rentalHome;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import salaba.dto.ReservedDateDto;
import salaba.entity.rental.QReservation;

import java.util.List;

//@RequiredArgsConstructor
//public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//    @Override
//    public List<ReservedDateDto> findReservedDate(Long rentalHomeId) {
//        queryFactory.select(QReservation.reservation);
//    }
//}
