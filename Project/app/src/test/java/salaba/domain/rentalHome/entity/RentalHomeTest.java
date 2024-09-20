package salaba.domain.rentalHome.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;

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

}