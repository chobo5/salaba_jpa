package salaba.domain.reservation.entity;

import org.junit.jupiter.api.Test;
import salaba.global.constants.ProcessStatus;
import salaba.global.entity.Address;
import salaba.global.entity.Nation;
import salaba.global.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.constants.PayMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    void 예약생성() {
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
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 21, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 23, 11, 0);
        Reservation reservation = Reservation.create(startDate, endDate, rentalHome, member);

        //then
        assertThat(reservation.getOriginalPrice()).isEqualTo(210000);
        assertThat(reservation.getRentalHome()).isEqualTo(rentalHome);
    }

    @Test
    void 예약취소() {
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
        Reservation reservation = Reservation.create(startDate, endDate, rentalHome, member);

        //when
        reservation.cancel();

        //then
        assertThat(reservation.getStatus()).isEqualTo(ProcessStatus.CANCEL);
    }

    @Test
    void 예약완료() {
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
        Reservation reservation = Reservation.create(startDate, endDate, rentalHome, member);
        Discount discount = Discount.create(reservation, 5000, "content");
        Discount discount2 = Discount.create(reservation, 6000, "content2");

        //when
        reservation.complete("123456", PayMethod.CARD);

        //then
        assertThat(reservation.getDiscounts().size()).isEqualTo(2);
        assertThat(reservation.getFinalPrice()).isEqualTo(199000);
        assertThat(reservation.getOriginalPrice()).isEqualTo(210000);
    }
}