package salaba.domain.reservation.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String content;

    public static Discount create(Reservation reservation, int amount, String content) {
        Discount discount = new Discount();
        discount.reservation = reservation;
        discount.amount = amount;
        discount.content = content;
        return discount;
    }
}
