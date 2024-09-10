package salaba.domain.reservation.entity;

import lombok.Getter;
import salaba.domain.common.entity.BaseEntity;
import salaba.domain.common.constants.ProcessStatus;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.Review;
import salaba.domain.reservation.constants.PayMethod;
import salaba.exception.CannotChangeStatusException;
import salaba.domain.rentalHome.entity.RentalHome;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true)
    private String paymentCode;

    @Column(nullable = false)
    private int originalPrice;

    @OneToMany(mappedBy = "reservation")
    private List<Discount> discounts = new ArrayList<>();

    @Column(nullable = false)
    private int finalPrice;

    @Enumerated(EnumType.STRING)
    private PayMethod method;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(mappedBy = "reservation")
    private Review review;


    public static Reservation createReservation(LocalDateTime startDate, LocalDateTime endDate, RentalHome rentalHome, Member member) {
        Reservation reservation = new Reservation();
        reservation.startDate = startDate;
        reservation.endDate = endDate;
        reservation.status = ProcessStatus.AWAIT;
        reservation.rentalHome = rentalHome;
        reservation.member = member;

        //예약 일수(체크인, 체크아웃 날짜 차이)
        int daysBetween = Period.between(endDate.toLocalDate(), startDate.toLocalDate()).getDays();
        //이용 가격 계산
        reservation.originalPrice = rentalHome.getPrice() * Math.abs(daysBetween);
        rentalHome.getReservations().add(reservation);
        return reservation;
    }

    public void cancelReservation() throws CannotChangeStatusException{
        if (status != ProcessStatus.AWAIT) {
            throw new CannotChangeStatusException();
        }
        status = ProcessStatus.CANCEL;
    }


    public void completePayment(String paymentCode, List<Discount> discounts, PayMethod method) {
        this.discounts.addAll(discounts);
        finalPrice = originalPrice;
        discounts.forEach(discount -> finalPrice -= discount.getAmount());
        status = ProcessStatus.COMPLETE;
        this.paymentCode = paymentCode;
        this.method = method;
    }


}