package salaba.domain.rentalHome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import salaba.config.QuerydslConfig;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.common.repository.NationRepository;
import salaba.domain.common.repository.RegionRepository;

import java.time.LocalDate;
import java.util.Optional;

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
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        rentalHomeRepository.save(rentalHome);

    }
    @Test
    public void 숙소상세() {
        //when

        //then
    }

    @Test
    public void 숙소상세() {
        //when

        //then

    }

    @Test
    public void 숙소상세_호스트용() {
        //when

        //then

    }

    @Test
    public void 호스트의숙소목록() {
        //when

        //then

    }

    @Test
    public void 숙소의예약목록() {
        //when

        //then

    }

    @Test
    public void 숙소찾기_리뷰순() {
        //when

        //then

    }

    @Test
    public void 숙소찾기_판매순() {
        //when

        //then

    }

    @Test
    public void 숙소찾기_리뷰순_DTO반환() {
        //when

        //then

    }

    @Test
    public void 숙소리뷰통계_전체업데이트() {
        //when

        //then

    }

    @Test
    public void 숙소리뷰통계_1개업데이트() {
        //when

        //then

    }

    @Test
    public void 숙소리뷰통계_합_평군업데이트() {
        //when

        //then

    }






}