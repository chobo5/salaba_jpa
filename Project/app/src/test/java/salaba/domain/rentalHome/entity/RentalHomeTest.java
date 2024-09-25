package salaba.domain.rentalHome.entity;

import org.junit.jupiter.api.Test;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.constants.RentalHomeStatus;
import salaba.domain.reservation.entity.Reservation;
import salaba.exception.CannotChangeStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalHomeTest {

    @Test
    public void 숙소생성() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        //when
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        //then
        assertThat(rentalHome.getHost()).isEqualTo(member);
        assertThat(rentalHome.getRegion()).isEqualTo(region);
        assertThat(rentalHome.getName()).isEqualTo("testHome");
        assertThat(rentalHome.getAddress()).isEqualTo(address);
    }

    @Test
    public void 숙소수정() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);


        //when
        Region modifiedRegion = new Region("제주", nation);
        String modifiedExplanation = "testHome_explanation22";
        rentalHome.modify(modifiedRegion, null, modifiedExplanation, null, null,
                null, null, null, null, null);

        //then
        assertThat(rentalHome.getRegion()).isEqualTo(modifiedRegion);
        assertThat(rentalHome.getExplanation()).isEqualTo(modifiedExplanation);
        assertThat(rentalHome.getName()).isEqualTo("testHome");
        assertThat(rentalHome.getPrice()).isEqualTo(100000);
        assertThat(rentalHome.getCapacity()).isEqualTo(4);
        assertThat(rentalHome.getLat()).isEqualTo(12.123421);
        assertThat(rentalHome.getLon()).isEqualTo(12.21321);
        assertThat(rentalHome.getRule()).isEqualTo("test rule");
        assertThat(rentalHome.getCleanFee()).isEqualTo(10000);

    }

    @Test
    public void 숙소_시설설정() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        List<RentalHomeFacility> facilities = new ArrayList<>();
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility1")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility2")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility3")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility4")));

        //when
        rentalHome.setFacilities(facilities);

        //then
        assertThat(rentalHome.getRentalHomeFacilities().size()).isEqualTo(4);

    }

    @Test
    public void 숙소_테마설정() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        List<RentalHomeTheme> themes = new ArrayList<>();
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme1")));
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme2")));
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme3")));
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme4")));
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme5")));
        themes.add(RentalHomeTheme.createRentalHomeTheme(rentalHome,new Theme("theme6")));

        //when
        rentalHome.setThemes(themes);

        //then
        assertThat(rentalHome.getRentalHomeThemes().size()).isEqualTo(6);
    }

    @Test
    public void 숙소폐쇄() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        LocalDateTime startDate = LocalDateTime.of(2024, 9, 21, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 23, 11, 0);
        Reservation reservation = Reservation.createReservation(startDate, endDate, rentalHome, member);

        //when
        rentalHome.closeRentalHome();

        assertThat(rentalHome.getStatus()).isEqualTo(RentalHomeStatus.DELETED);
    }

    @Test
    public void 숙소폐쇄_예약이있어실패() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        LocalDateTime startDate = LocalDateTime.of(2024, 9, 21, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 25, 11, 0);
        Reservation reservation = Reservation.createReservation(startDate, endDate, rentalHome, member);

        //when
        assertThrows(CannotChangeStatusException.class, () -> rentalHome.closeRentalHome());
        assertThat(rentalHome.getStatus()).isEqualTo(RentalHomeStatus.RUN);
    }

    @Test
    public void 숙소_리뷰통계수정() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        //when
        assertThat(rentalHome.getReviewAvg()).isEqualTo(0);
        assertThat(rentalHome.getReviewSum()).isEqualTo(0);
        assertThat(rentalHome.getReviewCount()).isEqualTo(0);
        rentalHome.updateReviewStatistics(4);
        assertThat(rentalHome.getReviewAvg()).isEqualTo(4);
        assertThat(rentalHome.getReviewSum()).isEqualTo(4);
        assertThat(rentalHome.getReviewCount()).isEqualTo(1);


    }

}