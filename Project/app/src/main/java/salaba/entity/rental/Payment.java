package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.ProcessStatus;
import salaba.exception.CannotChangeStatusException;

import javax.persistence.*;

@Entity
@Getter
public class Payment {
    @Id
    @Column(name = "payment_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private int amount;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    public static Payment createPayment(String id, Reservation reservation, int amount) {
        Payment payment = new Payment();
        payment.id = id;
        payment.reservation = reservation;
        payment.amount = amount;
        payment.status = ProcessStatus.AWAIT;
        return payment;
    }

    public void completePayment() {
        status = ProcessStatus.COMPLETE;
    }

    public void cancelPayment() throws CannotChangeStatusException {
        if (status != ProcessStatus.AWAIT) {
            throw new CannotChangeStatusException();
        }
        status = ProcessStatus.CANCEL;
        reservation.cancelReservation();
    }


}
