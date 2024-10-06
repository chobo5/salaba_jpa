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
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.global.repository.NationRepository;
import salaba.domain.global.repository.RegionRepository;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class RentalHomeRepositoryTest {
    @Autowired
    private RentalHomeRepository rentalHomeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void 숙소생성() {
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        memberRepository.save(member);

        Nation nation = new Nation(82, "kor");
        nationRepository.save(nation);
        Region region = new Region("서울", nation);
        regionRepository.save(region);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        rentalHomeRepository.save(rentalHome);

        RentalHome rentalHome2= RentalHome.create(member, region, "testHome2",
                "testHome_explanation2", address, 10000, 4, 12.123421, 12.21321,
                "test rule2", 10000);
        rentalHomeRepository.save(rentalHome2);

        RentalHome rentalHome3= RentalHome.create(member, region, "testHome3",
                "testHome_explanation3", address, 20000, 4, 12.123421, 12.21321,
                "test rule3", 10000);
        rentalHomeRepository.save(rentalHome3);

        Theme theme1 = new Theme("theme1");
        Theme theme2 = new Theme("theme2");
        Theme theme3 = new Theme("theme3");
        Theme theme4 = new Theme("theme4");
        em.persist(theme1);
        em.persist(theme2);
        em.persist(theme3);
        em.persist(theme4);

        Facility facility1 = new Facility("facility1");
        Facility facility2 = new Facility("facility2");
        Facility facility3 = new Facility("facility3");
        Facility facility4 = new Facility("facility4");

        em.persist(facility1);
        em.persist(facility2);
        em.persist(facility3);
        em.persist(facility4);

        RentalHomeTheme rentalHomeTheme1 = RentalHomeTheme.create(rentalHome, theme1);
        RentalHomeTheme rentalHomeTheme2 = RentalHomeTheme.create(rentalHome, theme2);
        RentalHomeTheme rentalHomeTheme3 = RentalHomeTheme.create(rentalHome, theme3);
        RentalHomeTheme rentalHomeTheme4 = RentalHomeTheme.create(rentalHome, theme4);
        RentalHomeTheme rentalHomeTheme5 = RentalHomeTheme.create(rentalHome2, theme2);
        RentalHomeTheme rentalHomeTheme6 = RentalHomeTheme.create(rentalHome2, theme3);
        RentalHomeTheme rentalHomeTheme7 = RentalHomeTheme.create(rentalHome2, theme4);
        RentalHomeTheme rentalHomeTheme8 = RentalHomeTheme.create(rentalHome3, theme1);
        RentalHomeTheme rentalHomeTheme9 = RentalHomeTheme.create(rentalHome3, theme2);
        em.persist(rentalHomeTheme1);
        em.persist(rentalHomeTheme2);
        em.persist(rentalHomeTheme3);
        em.persist(rentalHomeTheme4);
        em.persist(rentalHomeTheme5);
        em.persist(rentalHomeTheme6);
        em.persist(rentalHomeTheme7);
        em.persist(rentalHomeTheme8);
        em.persist(rentalHomeTheme9);

        RentalHomeFacility rentalHomeFacility1 = RentalHomeFacility.create(rentalHome, facility1);
        RentalHomeFacility rentalHomeFacility2 = RentalHomeFacility.create(rentalHome, facility2);
        RentalHomeFacility rentalHomeFacility3 = RentalHomeFacility.create(rentalHome, facility3);
        RentalHomeFacility rentalHomeFacility4 = RentalHomeFacility.create(rentalHome, facility4);
        RentalHomeFacility rentalHomeFacility5 = RentalHomeFacility.create(rentalHome2, facility2);
        RentalHomeFacility rentalHomeFacility6 = RentalHomeFacility.create(rentalHome2, facility3);
        RentalHomeFacility rentalHomeFacility7 = RentalHomeFacility.create(rentalHome2, facility4);
        RentalHomeFacility rentalHomeFacility8 = RentalHomeFacility.create(rentalHome3, facility2);
        RentalHomeFacility rentalHomeFacility9 = RentalHomeFacility.create(rentalHome3, facility3);

        em.persist(rentalHomeFacility1);
        em.persist(rentalHomeFacility2);
        em.persist(rentalHomeFacility3);
        em.persist(rentalHomeFacility4);
        em.persist(rentalHomeFacility5);
        em.persist(rentalHomeFacility6);
        em.persist(rentalHomeFacility7);
        em.persist(rentalHomeFacility8);
        em.persist(rentalHomeFacility9);

        Member member2 = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        memberRepository.save(member2);

        Member member3 = Member.create("test3@test.com", "Aa123456!@", "test3",
                "testNickname3", LocalDate.of(2000, 12, 12));
        memberRepository.save(member3);

        Member member4 = Member.create("test4@test.com", "Aa123456!@", "test4",
                "testNickname4", LocalDate.of(2000, 12, 12));
        memberRepository.save(member4);

        Member member5 = Member.create("test5@test.com", "Aa123456!@", "test5",
                "testNickname5", LocalDate.of(2000, 12, 12));
        memberRepository.save(member5);

        Member member6 = Member.create("test6@test.com", "Aa123456!@", "test6",
                "testNickname6", LocalDate.of(2000, 12, 12));
        memberRepository.save(member6);

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
    }
    @Test
    public void 숙소상세() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();

        //when
        RentalHomeDetailResDto rentalHome1 = rentalHomeRepository.findDetailById(rentalHomes.get(0).getId());
        RentalHomeDetailResDto rentalHome2 = rentalHomeRepository.findDetailById(rentalHomes.get(1).getId());
        RentalHomeDetailResDto rentalHome3 = rentalHomeRepository.findDetailById(rentalHomes.get(2).getId());

        //then
        assertThat(rentalHome1.getThemes().size()).isEqualTo(4);
        assertThat(rentalHome2.getThemes().size()).isEqualTo(3);
        assertThat(rentalHome3.getThemes().size()).isEqualTo(2);
        assertThat(rentalHome1.getFacilities().size()).isEqualTo(4);
        assertThat(rentalHome2.getFacilities().size()).isEqualTo(3);
        assertThat(rentalHome3.getFacilities().size()).isEqualTo(2);

    }

    @Test
    public void 숙소상세_호스트용() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();
        List<Member> members = memberRepository.findAll();
        //when
        RentalHomeDetailResDto rentalHome1 = rentalHomeRepository.findDetailByIdAndHost(rentalHomes.get(0).getId(), members.get(0).getId());
        RentalHomeDetailResDto rentalHome2 = rentalHomeRepository.findDetailByIdAndHost(rentalHomes.get(1).getId(), members.get(0).getId());
        RentalHomeDetailResDto rentalHome3 = rentalHomeRepository.findDetailByIdAndHost(rentalHomes.get(2).getId(), members.get(0).getId());

        //then
        assertThat(rentalHome1.getThemes().size()).isEqualTo(4);
        assertThat(rentalHome2.getThemes().size()).isEqualTo(3);
        assertThat(rentalHome3.getThemes().size()).isEqualTo(2);
        assertThat(rentalHome1.getFacilities().size()).isEqualTo(4);
        assertThat(rentalHome2.getFacilities().size()).isEqualTo(3);
        assertThat(rentalHome3.getFacilities().size()).isEqualTo(2);

    }

    @Test
    public void 호스트의숙소목록() {
        //given
        List<Member> members = memberRepository.findAll();
        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<RentalHome> rentalHomes = rentalHomeRepository.findByHost(members.get(0).getId(), pageable);

        //then
        assertThat(rentalHomes.getTotalElements()).isEqualTo(3);

    }

    @Test
    public void 숙소의예약목록과함께() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();
        //when
        RentalHome rentalHome1 = rentalHomeRepository.findWithReservations(rentalHomes.get(0).getId()).orElseThrow(EntityNotFoundException::new);
        RentalHome rentalHome2 = rentalHomeRepository.findWithReservations(rentalHomes.get(1).getId()).orElseThrow(EntityNotFoundException::new);
        RentalHome rentalHome3 = rentalHomeRepository.findWithReservations(rentalHomes.get(2).getId()).orElseThrow(EntityNotFoundException::new);


        //then
        assertThat(rentalHome1.getReservations().size()).isEqualTo(5);
        assertThat(rentalHome2.getReservations().size()).isEqualTo(5);
        assertThat(rentalHome3.getReservations()).isEmpty();
    }

    @Test
    public void 숙소찾기_리뷰순() {
        //given
        String region = "서울";
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<RentalHome> rentalHomes = rentalHomeRepository.findRentalHomesOrderByReview(region, null, null, null, pageable);

        //then
        assertThat(rentalHomes.getTotalElements()).isEqualTo(3);
        assertThat(rentalHomes.getContent().get(0).getName()).isEqualTo("testHome");
        assertThat(rentalHomes.getContent().get(1).getName()).isEqualTo("testHome2");
        assertThat(rentalHomes.getContent().get(2).getName()).isEqualTo("testHome3");

    }

    @Test
    public void 숙소찾기_판매순() {
        //given
        String region = "서울";
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<RentalHome> rentalHomes = rentalHomeRepository.findRentalHomesOrderByReview(region, null, null, null, pageable);

        //then
        assertThat(rentalHomes.getTotalElements()).isEqualTo(3);
        assertThat(rentalHomes.getContent().get(2).getName()).isEqualTo("testHome3");
    }

    @Test
    public void 숙소찾기_리뷰순_DTO반환() {
        //given
        String region = "서울";
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<RentalHomeResDto> rentalHomes = rentalHomeRepository.findRentalHomeDtosOrderByReview(region, null, null, null, pageable);

        //then
        assertThat(rentalHomes.getTotalElements()).isEqualTo(3);
        assertThat(rentalHomes.getContent().get(0).getName()).isEqualTo("testHome");
        assertThat(rentalHomes.getContent().get(1).getName()).isEqualTo("testHome2");
        assertThat(rentalHomes.getContent().get(2).getName()).isEqualTo("testHome3");

    }

    @Test
    public void 숙소리뷰통계_전체업데이트() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();

        //when
        rentalHomeRepository.updateReviewStatistics();
        RentalHome rentalHome1 = em.find(RentalHome.class, rentalHomes.get(0).getId());
        RentalHome rentalHome2 = em.find(RentalHome.class, rentalHomes.get(1).getId());
        RentalHome rentalHome3 = em.find(RentalHome.class, rentalHomes.get(2).getId());

        //then
        assertThat(rentalHome1.getReviewAvg()).isEqualTo(3.4);
        assertThat(rentalHome2.getReviewAvg()).isEqualTo(3.0);
        assertThat(rentalHome3.getReviewAvg()).isEqualTo(0.0);

        assertThat(rentalHome1.getReviewCount()).isEqualTo(5);
        assertThat(rentalHome2.getReviewCount()).isEqualTo(5);
        assertThat(rentalHome3.getReviewCount()).isEqualTo(0);

        assertThat(rentalHome1.getReviewSum()).isEqualTo(17);
        assertThat(rentalHome2.getReviewSum()).isEqualTo(15);
        assertThat(rentalHome3.getReviewSum()).isEqualTo(0);

    }

    @Test
    public void 숙소리뷰통계_1개업데이트() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();

        //when
        RentalHome rentalHome1 = em.find(RentalHome.class, rentalHomes.get(0).getId());
        rentalHomeRepository.updateAReviewStatistics(rentalHome1);

        //then
        assertThat(rentalHome1.getReviewAvg()).isEqualTo(3.4);
        assertThat(rentalHome1.getReviewCount()).isEqualTo(5);
        assertThat(rentalHome1.getReviewSum()).isEqualTo(17);
    }

    @Test
    public void 숙소리뷰통계_합_평군업데이트() {
        //given
        List<RentalHome> rentalHomes = rentalHomeRepository.findAll();

        //when
        RentalHome rentalHome1 = em.find(RentalHome.class, rentalHomes.get(0).getId());
        rentalHomeRepository.updateAReviewStatistics(rentalHome1);

        //then
        assertThat(rentalHome1.getReviewAvg()).isEqualTo(3.4);
        assertThat(rentalHome1.getReviewSum()).isEqualTo(17);

    }






}