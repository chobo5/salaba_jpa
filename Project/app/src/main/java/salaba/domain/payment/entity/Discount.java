package salaba.domain.payment.entity;

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
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String content;

    public static Discount createDiscount(Payment payment, int amount, String content) {
        Discount discount = new Discount();
        discount.payment = payment;
        discount.amount = amount;
        discount.content = content;
        return discount;
    }
}
