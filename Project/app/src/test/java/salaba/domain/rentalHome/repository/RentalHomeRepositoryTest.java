package salaba.domain.rentalHome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.common.repository.NationRepository;
import salaba.domain.common.repository.RegionRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

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

//    @Test
//    public void 호스트의숙소리스트() {
//        //given
//        Member member = memberRepository.findByEmail("test@test.com").get();
//        Pageable pageable = PageRequest.of(0, 10);
//        //when
//        Page<RentalHome> result = rentalHomeRepository.findByHost(member.getId(), pageable);
//
//        //then
//
//    }
//
//    @Test
//    public void 숙소상세() {
//        //given
//        Member member = memberRepository.findByEmail("test@test.com").get();
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<RentalHome> rentalHomes = rentalHomeRepository.findByHost(member.getId(), pageable);
//
//        //when
//        RentalHomeDetailResDto result = rentalHomeRepository.findDetailById(rentalHomes.getContent().get(0).getId());
//
//        //then
//    }


}