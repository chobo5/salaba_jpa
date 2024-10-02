package salaba.domain.reservation.entity;

import lombok.Getter;
import salaba.global.entity.BaseEntity;
import salaba.global.constants.WritingStatus;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "rental_home_review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private int score;
    private String content;

    @Enumerated(EnumType.STRING)
    private WritingStatus status;

    public static Review createReview(Reservation reservation, int score, String content) {
        Review review = new Review();
        review.reservation = reservation;
        review.score = score;
        review.content = content;
        review.status = WritingStatus.NORMAL;
        reservation.getRentalHome().updateReviewStatistics(score);
        return review;
    }

    public void modifyReview(String content, Integer score) {
        this.content = content != null ? content : this.content;
        this.score = score != null ? score : this.score;
    }
}
