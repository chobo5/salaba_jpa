package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.domain.member.entity.Point;
import salaba.domain.member.repository.PointRepository;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.entity.Review;
import salaba.domain.rentalHome.repository.ReviewRepository;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.ReservationRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final PointRepository pointRepository;

    public Long createReview(ReviewReqDto reviewReqDto) {
        Reservation reservation = reservationRepository.findByIdWithMember(reviewReqDto.getReservationId()).orElseThrow(NoSuchElementException::new);
        Review review = Review.createReview(reservation, reviewReqDto.getScore(), reviewReqDto.getContent());
        reviewRepository.save(review);

        Point point = Point.createReviewPoint(reservation.getMember());
        pointRepository.save(point);

        return review.getId();
    }
}
