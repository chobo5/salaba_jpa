package salaba.domain.rentalHome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.PointService;
import salaba.domain.rentalHome.dto.request.ReviewModiReqDto;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Review;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.rentalHome.repository.ReviewRepository;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PointService pointService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RentalHomeRepository rentalHomeRepository;

    @Test
    void 리뷰생성() {
        //given
        Long memberId = 1L;
        ReviewReqDto reqDto = new ReviewReqDto(1L, "review content", 5);

        //when
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));

        Region region = new Region("서울", new Nation(82, "kor"));
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(guest));
        when(reservationRepository.findByIdWithMemberAndRentalHome(1L)).thenReturn(Optional.of(reservation));
        doNothing().when(pointService).createReviewPoint(guest);

        reviewService.createReview(reqDto, memberId);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(reservationRepository, times(1)).findByIdWithMemberAndRentalHome(1L);
        verify(pointService, times(1)).createReviewPoint(guest);
        verify(reviewRepository, times(1)).save(any(Review.class));

    }

    @Test
    void 리뷰삭제() {
        //given
        Long memberId = 1L;
        Long reviewId = 1L;

        //when
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Region region = new Region("서울", new Nation(82, "kor"));
        Address address = new Address("test street", 123412);
        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);
        Review review = Review.createReview(reservation, 5, "review content");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(guest));
        when(reviewRepository.findByIdWithReservationAndMemberAndRentalHome(1L)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(review);
        doNothing().when(rentalHomeRepository).updateAReviewStatistics(rentalHome);

        reviewService.deleteReview(reviewId, memberId);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(reviewRepository, times(1)).findByIdWithReservationAndMemberAndRentalHome(1L);
        verify(reviewRepository, times(1)).delete(review);
        verify(rentalHomeRepository, times(1)).updateAReviewStatistics(rentalHome);
    }

    @Test
    void 리뷰수정() {
        //given
        Long memberId = 1L;
        ReviewModiReqDto reqDto = new ReviewModiReqDto(1L, "modified content", 4);

        //when
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Region region = new Region("서울", new Nation(82, "kor"));
        Address address = new Address("test street", 123412);
        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);
        Review review = Review.createReview(reservation, 5, "review content");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(guest));
        when(reviewRepository.findByIdWithReservationAndMemberAndRentalHome(1L)).thenReturn(Optional.of(review));
        doNothing().when(rentalHomeRepository).updateAReviewAvgAndSum(rentalHome);

        reviewService.modifyReview(reqDto, memberId);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(reviewRepository, times(1)).findByIdWithReservationAndMemberAndRentalHome(1L);
        verify(rentalHomeRepository, times(1)).updateAReviewAvgAndSum(rentalHome);
    }

    @Test
    void 숙소리뷰목록() {
        //given
        Long rentalHomeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Region region = new Region("서울", new Nation(82, "kor"));
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);
        Page<Review> reviews = new PageImpl<>(Arrays.asList(Review.createReview(reservation, 5, "review content")));
        when(rentalHomeRepository.findById(rentalHomeId)).thenReturn(Optional.of(rentalHome));

        when(reviewRepository.findByRentalHome(rentalHome, pageable)).thenReturn(reviews);

        reviewService.getRentalHomeReviews(rentalHomeId, pageable);

        //then
        verify(rentalHomeRepository, times(1)).findById(rentalHomeId);
        verify(reviewRepository, times(1)).findByRentalHome(rentalHome, pageable);
    }

    @Test
    void 숙소리뷰평균() {
        //given
        Long rentalHomeId = 1L;

        //when
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Region region = new Region("서울", new Nation(82, "kor"));
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        when(rentalHomeRepository.findById(rentalHomeId)).thenReturn(Optional.of(rentalHome));
        when(reviewRepository.getReviewAvg(rentalHome)).thenReturn(4.7);

        reviewService.getRentalHomeReviewAvg(rentalHomeId);

        //then
        verify(rentalHomeRepository, times(1)).findById(rentalHomeId);
        verify(reviewRepository, times(1)).getReviewAvg(rentalHome);
    }
}