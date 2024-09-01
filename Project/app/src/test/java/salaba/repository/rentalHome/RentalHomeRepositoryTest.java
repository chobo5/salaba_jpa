package salaba.repository.rentalHome;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.response.RentalHomeDetailResDto;
import salaba.entity.Address;
import salaba.entity.Nation;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.repository.MemberRepository;
import salaba.repository.NationRepository;
import salaba.repository.RegionRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RentalHomeRepositoryTest {
    @Autowired
    RentalHomeRepository rentalHomeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    RegionRepository regionRepository;

    @BeforeEach
    public void 초기화() {
        Member member = Member.createMember("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        memberRepository.save(member);

        Nation nation = new Nation(82, "kor");
        nationRepository.save(nation);
        Region region = new Region("서울", nation);
        regionRepository.save(region);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        rentalHomeRepository.save(rentalHome);

        RentalHome rentalHome2 = RentalHome.createRentalHome(member, region, "testHome2",
                "testHome_explanation2", address, 100000, 4, 12.123421, 12.21321,
                "test rule2", 10000);
        rentalHomeRepository.save(rentalHome2);
    }

    @Test
    public void 호스트의숙소리스트() {
        //given
        Member member = memberRepository.findByEmail("test@test.com").get();

        //when
        List<RentalHome> result = rentalHomeRepository.findByHost(member.getId());

        //then
        assertThat(result.get(0).getHost()).isEqualTo(member);
        assertThat(result.size()).isEqualTo(2);


    }

    @Test
    public void 숙소상세() {
        //given
        Member member = memberRepository.findByEmail("test@test.com").get();
        List<RentalHome> rentalHomes = rentalHomeRepository.findByHost(member.getId());

        //when
        RentalHomeDetailResDto result = rentalHomeRepository.get(rentalHomes.get(0).getId());

        //then
        assertThat(result.getName()).isEqualTo("testHome2");
        assertThat(result.getExplanation()).isEqualTo("testHome_explanation2");
    }


}