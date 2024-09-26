package salaba.domain.rentalHome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.reservation.entity.Reservation;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
@ActiveProfiles("test")
class ReviewRepositoryTest {
    @Autowired
    private EntityManager em;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @BeforeEach
    public void 숙소생성() {
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        em.persist(member);

        Nation nation = new Nation(82, "kor");
        em.persist(nation);
        Region region = new Region("서울", nation);
        em.persist(region);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        em.persist(rentalHome);

        RentalHome rentalHome2= RentalHome.create(member, region, "testHome2",
                "testHome_explanation2", address, 10000, 4, 12.123421, 12.21321,
                "test rule2", 10000);
        em.persist(rentalHome2);

        RentalHome rentalHome3= RentalHome.create(member, region, "testHome3",
                "testHome_explanation3", address, 20000, 4, 12.123421, 12.21321,
                "test rule3", 10000);
        em.persist(rentalHome3);


        

        Member member2 = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        em.persist(member2);

        Member member3 = Member.create("test3@test.com", "Aa123456!@", "test3",
                "testNickname3", LocalDate.of(2000, 12, 12));
        em.persist(member3);

        Member member4 = Member.create("test4@test.com", "Aa123456!@", "test4",
                "testNickname4", LocalDate.of(2000, 12, 12));
        em.persist(member4);

        Member member5 = Member.create("test5@test.com", "Aa123456!@", "test5",
                "testNickname5", LocalDate.of(2000, 12, 12));
        em.persist(member5);

        Member member6 = Member.create("test6@test.com", "Aa123456!@", "test6",
                "testNickname6", LocalDate.of(2000, 12, 12));
        em.persist(member6);

        LocalDateTime startDate1 = LocalDateTime.of(2024, 9, 12 ,15, 0);
        LocalDateTime startDate2 = LocalDateTime.of(2024, 9, 15 ,15, 0);
        LocalDateTime startDate3 = LocalDateTime.of(2024, 9, 17 ,15, 0);
        LocalDateTime startDate4 = LocalDateTime.of(2024, 9, 20 ,15, 0);
        LocalDateTime startDate5 = LocalDateTime.of(2024, 9, 25 ,15, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2024, 9, 15 ,11, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2024, 9, 17 ,11, 0);
        LocalDateTime endDate3 = LocalDateTime.of(2024, 9, 20 ,11, 0);
        LocalDateTime endDate4 = LocalDateTime.of(2024, 9, 25 ,11, 0);
        LocalDateTime endDate5 = LocalDateTime.of(2024, 9, 28 ,11, 0);

        Reservation reservation1 = Reservation.create(startDate1, endDate1, rentalHome, member2);
        Reservation reservation2 = Reservation.create(startDate2, endDate2, rentalHome, member3);
        Reservation reservation3 = Reservation.create(startDate3, endDate3, rentalHome, member4);
        Reservation reservation4 = Reservation.create(startDate4, endDate4, rentalHome, member5);
        Reservation reservation5 = Reservation.create(startDate5, endDate5, rentalHome, member6);

        Reservation reservation6 = Reservation.create(startDate5, endDate5, rentalHome2, member2);
        Reservation reservation7 = Reservation.create(startDate4, endDate4, rentalHome2, member3);
        Reservation reservation8 = Reservation.create(startDate3, endDate3, rentalHome2, member4);
        Reservation reservation9 = Reservation.create(startDate2, endDate2, rentalHome2, member5);
        Reservation reservation10 = Reservation.create(startDate1, endDate1, rentalHome2, member6);
        em.persist(reservation1);
        em.persist(reservation2);
        em.persist(reservation3);
        em.persist(reservation4);
        em.persist(reservation5);
        em.persist(reservation6);
        em.persist(reservation7);
        em.persist(reservation8);
        em.persist(reservation9);
        em.persist(reservation10);

        Review review1 = Review.createReview(reservation1, 5, "review1");
        Review review2 = Review.createReview(reservation2, 4, "review2");
        Review review3 = Review.createReview(reservation3, 3, "review3");
        Review review4 = Review.createReview(reservation4, 2, "review4");
        Review review5 = Review.createReview(reservation5, 3, "review5");
        Review review6 = Review.createReview(reservation6, 4, "review6");
        Review review7 = Review.createReview(reservation7, 1, "review7");
        Review review8 = Review.createReview(reservation8, 3, "review8");
        Review review9 = Review.createReview(reservation9, 4, "review9");
        Review review10 = Review.createReview(reservation10, 3, "review10");

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);
        reviewRepository.save(review5);
        reviewRepository.save(review6);
        reviewRepository.save(review7);
        reviewRepository.save(review8);
        reviewRepository.save(review9);
        reviewRepository.save(review10);
    }

    @Test
    public void 회원의리뷰목록() {
        //given
        Member member = em.find(Member.class, 2L);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Review> reviews = reviewRepository.findByMember(member, pageable);

        //then
        assertThat(reviews.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void 숙소의리뷰목록() {
        //given
        RentalHome rentalHome = em.find(RentalHome.class, 1L);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Review> reviews = reviewRepository.findByRentalHome(rentalHome, pageable);

        //then
        assertThat(reviews.getTotalElements()).isEqualTo(5);

    }

    @Test
    public void 숙소의리뷰평점() {
        //given
        RentalHome rentalHome = em.find(RentalHome.class, 1L);

        //when
        Double reviewAvg = reviewRepository.getReviewAvg(rentalHome);

        //then
        assertThat(reviewAvg).isEqualTo(3.4);
    }

    @Test
    public void 리뷰_예약_회원_숙소가져오기() {
        //given
        Member member = em.find(Member.class, 2L);
        RentalHome rentalHome = em.find(RentalHome.class, 1L);
        Reservation reservation = em.find(Reservation.class, 1L);
        //when
        Review review = reviewRepository.findByIdWithReservationAndMemberAndRentalHome(1L).orElseThrow(NoSuchElementException::new);

        //then
        assertThat(review.getReservation()).isEqualTo(reservation);
        assertThat(review.getReservation().getRentalHome()).isEqualTo(rentalHome);
        assertThat(review.getReservation().getMember()).isEqualTo(member);
    }

}