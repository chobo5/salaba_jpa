package salaba.domain.reservation.entity;

import lombok.Getter;
import salaba.domain.common.entity.BaseEntity;
import salaba.domain.common.constants.ProcessStatus;
import salaba.domain.member.entity.Member;
import salaba.exception.CannotChangeStatusException;
import salaba.domain.rentalHome.entity.RentalHome;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public static Reservation createReservation(LocalDateTime startDate, LocalDateTime endDate, RentalHome rentalHome, Member member) {
        Reservation reservation = new Reservation();
        reservation.startDate = startDate;
        reservation.endDate = endDate;
        reservation.status = ProcessStatus.AWAIT;
        reservation.rentalHome = rentalHome;
        reservation.member = member;
        rentalHome.getReservations().add(reservation);
        return reservation;
    }

    public void cancelReservation() throws CannotChangeStatusException{
        if (status != ProcessStatus.AWAIT) {
            throw new CannotChangeStatusException();
        }
        status = ProcessStatus.CANCEL;
    }
}
