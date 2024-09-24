package salaba.domain.rentalHome.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;
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
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, "testHome",
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


        RentalHome rentalHome = RentalHome.createRentalHome(member, region, "testHome",
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

    public void 숙소_시설설정() {
        //given
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);


        RentalHome rentalHome = RentalHome.createRentalHome(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        List<RentalHomeFacility> facilities = new ArrayList<>();
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility1")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility2")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility3")));
        facilities.add(RentalHomeFacility.createRentalHomeFacility(rentalHome,new Facility("facility4")));


        rentalHome.setFacilities(facilities);

        assertThat(rentalHome.getRentalHomeFacilities().size()).isEqualTo(4);

    }

    public void 숙소_테마설정() {

    }

}