package salaba.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.PointService;
import salaba.domain.rentalHome.dto.request.ReviewModiReqDto;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.dto.response.ReviewResDto;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.repository.query.RentalHomeQueryRepository;
import salaba.domain.reservation.entity.Review;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.reservation.repository.ReviewRepository;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.auth.exception.NoAuthorityException;
import salaba.domain.reservation.repository.query.ReservationQueryRepository;
import salaba.domain.reservation.repository.query.ReviewQueryRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PointService pointService;
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final RentalHomeQueryRepository rentalHomeQueryRepository;
    private final ReservationQueryRepository reservationQueryRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public Long createReview(ReviewReqDto reviewReqDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reservation reservation = reservationQueryRepository.findByIdWithMemberAndRentalHome(reviewReqDto.getReservationId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reservation.class, reviewReqDto.getReservationId())));

        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("리뷰 작성 권한이 없습니다.");
        }

        Review review = Review.createReview(reservation, reviewReqDto.getScore(), reviewReqDto.getContent());
        reviewRepository.save(review);

        pointService.createReviewPoint(reservation.getMember());

        return review.getId();
    }

    public void deleteReview(Long reviewId, Long memberId) {
        Review findReview = reviewQueryRepository.findByIdWithReservationAndMemberAndRentalHome(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Review.class, reviewId)));

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        if (!findReview.getReservation().getMember().equals(findMember)) {
            throw new NoAuthorityException("리뷰 삭제 권한이 없습니다.");
        }
        reviewRepository.delete(findReview);
        rentalHomeQueryRepository.updateAReviewStatistics(findReview.getReservation().getRentalHome());

    }

    public void modifyReview(ReviewModiReqDto reqDto, Long memberId) {
        Review findReview = reviewQueryRepository.findByIdWithReservationAndMemberAndRentalHome(reqDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Review.class, reqDto.getReviewId())));

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        if (!findReview.getReservation().getMember().equals(findMember)) {
            throw new NoAuthorityException("리뷰 수정 권한이 없습니다.");
        }
        findReview.modifyReview(reqDto.getContent(), reqDto.getScore());
        rentalHomeQueryRepository.updateAReviewAvgAndSum(findReview.getReservation().getRentalHome());

    }

    public Page<ReviewResDto> getRentalHomeReviews(Long rentalHomeId, Pageable pageable) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));

        Page<Review> reviews = reviewQueryRepository.findByRentalHome(rentalHome, pageable);
        return reviews.map(ReviewResDto::new);
    }

    public Double getRentalHomeReviewAvg(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));

        return reviewQueryRepository.getReviewAvg(rentalHome);
    }
}
