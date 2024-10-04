package salaba.domain.rentalHome.entity;

import org.junit.jupiter.api.Test;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.entity.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {
    @Test
    public void 리뷰생성() {
        //given
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);

        //when
        Review review = Review.createReview(reservation, 5, "review content");

        //then
        assertThat(rentalHome.getReviewSum()).isEqualTo(5);
        assertThat(rentalHome.getReviewAvg()).isEqualTo(5);
        assertThat(rentalHome.getReviewCount()).isEqualTo(1);
    }

    @Test
    public void 리뷰수정() {
        //given
        Member host = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Member guest = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(host, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, guest);

        Review review = Review.createReview(reservation, 5, "review content");

        //when
        review.modifyReview("modified content", 4);

        //then
        assertThat(rentalHome.getReviewSum()).isEqualTo(5);
        assertThat(rentalHome.getReviewAvg()).isEqualTo(5);
        assertThat(rentalHome.getReviewCount()).isEqualTo(1);
        assertThat(review.getContent()).isEqualTo("modified content");
    }

}