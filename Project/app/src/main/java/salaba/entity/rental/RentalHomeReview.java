package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.board.WritingStatus;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomeReview {
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


}
