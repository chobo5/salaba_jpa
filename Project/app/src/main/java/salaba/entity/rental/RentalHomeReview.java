package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.BaseEntity;
import salaba.entity.board.WritingStatus;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomeReview extends BaseEntity {
    @Id
    @Column(name = "rental_home_review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private int score;
    private String content;

    @Enumerated(EnumType.STRING)
    private WritingStatus status;

    public static RentalHomeReview createRentalHomeReview(Reservation reservation, int score, String content) {
        RentalHomeReview rentalHomeReview = new RentalHomeReview();
        rentalHomeReview.reservation = reservation;
        rentalHomeReview.score = score;
        rentalHomeReview.content = content;
        rentalHomeReview.status = WritingStatus.NORMAL;
        return rentalHomeReview;
    }
}
