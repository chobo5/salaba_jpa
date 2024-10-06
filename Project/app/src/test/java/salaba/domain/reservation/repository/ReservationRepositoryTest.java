package salaba.domain.reservation.repository;

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
import salaba.domain.global.constants.ProcessStatus;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.entity.Discount;
import salaba.domain.reservation.entity.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QuerydslConfig.class)
@DataJpaTest
@ActiveProfiles("test")
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
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

        LocalDateTime startDate = LocalDateTime.of(2024, 9, 21, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 23, 11, 0);
        Reservation reservation = Reservation.create(startDate, endDate, rentalHome, member);
        reservationRepository.save(reservation);
        Discount discount = Discount.create(reservation, 5000, "content");
        em.persist(discount);
        Discount discount2 = Discount.create(reservation, 6000, "content2");
        em.persist(discount2);
    }

    @Test
    void 특정상태인숙소의예약목록() {
        //when
        List<RentalHome> rentalHomes = em.createQuery("select r from RentalHome r", RentalHome.class).getResultList();
        RentalHome rentalHome = rentalHomes.get(0);
        List<Reservation> reservations = reservationRepository.findByRentalHomeAndStatus(rentalHome, ProcessStatus.AWAIT);

        //then
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.get(0).getOriginalPrice()).isEqualTo(210000);

    }

    @Test
    public void 게스트포함예약목록() {
        //given
        List<RentalHome> rentalHomes = em.createQuery("select r from RentalHome r", RentalHome.class).getResultList();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Reservation> reservations = reservationRepository.findWithGuest(rentalHomes.get(0).getId(), pageable);

        //then
        assertThat(reservations.getTotalElements()).isEqualTo(1);
        assertThat(reservations.getContent().get(0).getMember().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    public void 숙소_호스트_포함예약목록() {
        //given
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Reservation> reservations = reservationRepository.findWithRentalHomeAndHost(members.get(0).getId(), pageable);

        assertThat(reservations.getTotalElements()).isEqualTo(1);
        assertThat(reservations.getContent().get(0).getRentalHome()).isNotNull();
        assertThat(reservations.getContent().get(0).getRentalHome().getHost()).isNotNull();
    }

    @Test
    public void 숙소_게스트_포함예약목록() {
        //given
        List<Reservation> reservations = em.createQuery("select r from Reservation r", Reservation.class).getResultList();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Reservation reservation = reservationRepository.findByIdWithMemberAndRentalHome(reservations.get(0).getId())
                .orElseThrow(EntityNotFoundException::new);

        //then
        assertThat(reservation.getMember()).isNotNull();
        assertThat(reservation.getRentalHome()).isNotNull();
    }


}