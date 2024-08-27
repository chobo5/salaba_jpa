package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.ProcessStatus;
import salaba.exception.CannotChangeStatusException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Payment {
    @Id
    @Column(name = "payment_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private int originalPrice;

    @OneToMany(mappedBy = "payment")
    private List<Discount> discounts;

    @Column(nullable = false)
    private int finalPrice;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Enumerated(EnumType.STRING)
    private PayMethod method;

    public static Payment createPayment(Reservation reservation, int originalPrice) {
        Payment payment = new Payment();
        payment.id = String.valueOf(reservation.getId());
        payment.reservation = reservation;
        payment.originalPrice = originalPrice;
        payment.status = ProcessStatus.AWAIT;
        return payment;
    }

    public String completePayment(String paymentId, List<Discount> discounts, PayMethod method) {
        this.id = paymentId;
        this.discounts = discounts;
        finalPrice = originalPrice;
        discounts.forEach(discount -> finalPrice -= discount.getAmount());
        status = ProcessStatus.COMPLETE;
        this.method = method;
        return paymentId;
    }

    public void cancelPayment() throws CannotChangeStatusException {
        if (status != ProcessStatus.AWAIT) {
            throw new CannotChangeStatusException();
        }
        status = ProcessStatus.CANCEL;
        reservation.cancelReservation();
    }


}
