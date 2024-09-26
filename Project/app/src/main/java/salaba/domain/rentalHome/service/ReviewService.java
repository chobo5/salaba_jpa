package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.PointService;
import salaba.domain.rentalHome.dto.request.ReviewModiReqDto;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.dto.response.ReviewResDto;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Review;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.rentalHome.repository.ReviewRepository;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.ReservationRepository;
import salaba.exception.CannotFindMemberException;
import salaba.exception.NoAuthorityException;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final PointService pointService;
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;

    public Long createReview(ReviewReqDto reviewReqDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(reviewReqDto.getReservationId()).orElseThrow(NoSuchElementException::new);

        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("리뷰 작성 권한이 없습니다.");
        }

        Review review = Review.createReview(reservation, reviewReqDto.getScore(), reviewReqDto.getContent());
        reviewRepository.save(review);

        pointService.createReviewPoint(reservation.getMember());

        return review.getId();
    }

    public void deleteReview(Long reviewId, Long memberId) {
        Review findReview = reviewRepository.findByIdWithReservationAndMemberAndRentalHome(reviewId).orElseThrow(NoSuchElementException::new);
        Member findMember = memberRepository.findById(memberId).orElseThrow(CannotFindMemberException::new);
        if (!findReview.getReservation().getMember().equals(findMember)) {
            throw new NoAuthorityException("리뷰 삭제 권한이 없습니다.");
        }
        reviewRepository.delete(findReview);
        rentalHomeRepository.updateAReviewStatistics(findReview.getReservation().getRentalHome());

    }

    public void modifyReview(ReviewModiReqDto reqDto, Long memberId) {
        Review findReview = reviewRepository.findByIdWithReservationAndMemberAndRentalHome(reqDto.getReviewId()).orElseThrow(NoSuchElementException::new);
        Member findMember = memberRepository.findById(memberId).orElseThrow(CannotFindMemberException::new);
        if (!findReview.getReservation().getMember().equals(findMember)) {
            throw new NoAuthorityException("리뷰 수정 권한이 없습니다.");
        }
        findReview.modifyReview(reqDto.getContent(), reqDto.getScore());
        rentalHomeRepository.updateAReviewAvgAndSum(findReview.getReservation().getRentalHome());

    }

    public Page<ReviewResDto> getRentalHomeReviews(Long rentalHomeId, Pageable pageable) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        Page<Review> reviews = reviewRepository.findByRentalHome(rentalHome, pageable);
        return reviews.map(ReviewResDto::new);
    }

    public Double getRentalHomeReviewAvg(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        return reviewRepository.getReviewAvg(rentalHome);
    }
}
