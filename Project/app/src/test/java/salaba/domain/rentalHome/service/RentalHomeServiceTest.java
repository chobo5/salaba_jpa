package salaba.domain.rentalHome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.common.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.constants.RentalHomeStatus;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.Facility;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.RentalHomeTheme;
import salaba.domain.rentalHome.entity.Theme;
import salaba.domain.rentalHome.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalHomeServiceTest {
    @InjectMocks
    private RentalHomeService rentalHomeService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RentalHomeRepository rentalHomeRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private RentalHomeFacilityRepository rentalHomeFacilityRepository;

    @Mock
    private RentalHomeThemeRepository rentalHomeThemeRepository;

    @Mock
    private ReviewService reviewService;

    @Test
    void 숙소상세() {
        //given
        Long rentalHomeId = 1L;

        //when
        List<String> themes = new ArrayList<>();
        themes.add("theme");
        List<String> facilities = new ArrayList<>();
        themes.add("facility");
        RentalHomeDetailResDto resDto = new RentalHomeDetailResDto(rentalHomeId, "testHome", "host", "123-123123",
                new Address("testStreet", 123123), 4, 10000, "testExplanation", 123.3213, 123.43143,
                100000, "rule","서울",
                themes, facilities);

        when(rentalHomeRepository.findDetailById(rentalHomeId)).thenReturn(resDto);

        rentalHomeService.getRentalHome(rentalHomeId);

        //then
        verify(rentalHomeRepository, times(1)).findDetailById(rentalHomeId);
    }

    @Test
    void 숙소등록() {
        //given
        List<Long> themes = new ArrayList<>();
        List<Long> facilities = new ArrayList<>();
        RentalHomeCreateReqDto reqDto = new RentalHomeCreateReqDto("testHome", "testStreet", 123123, 4, 10000, "testtest",
                123.12321, 232.312313, 100000, "rule", 1L, themes, facilities);

        //when
        Member member = Member.create("test@test.com", "Aabcd1231312", "testName",
                "testNickname", LocalDate.of(1999, 10, 10));
        Region region = new Region("제주", new Nation(82, "korea"));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(regionRepository.findById(anyLong())).thenReturn(Optional.of(region));

        RentalHome rentalHome = RentalHome.create(member, region, reqDto.getName(),
                reqDto.getExplanation(), new Address(reqDto.getStreet(), reqDto.getZipcode()), reqDto.getPrice(),
                reqDto.getCapacity(), reqDto.getLat(), reqDto.getLon(), reqDto.getRule(), reqDto.getCleanFee());

        when(rentalHomeRepository.save(any(RentalHome.class))).thenReturn(rentalHome);
        when(themeRepository.findAllById(reqDto.getThemes())).thenReturn(new ArrayList<Theme>());
        when((rentalHomeThemeRepository.saveAll(any(List.class))));
        when(facilityRepository.findAllById(reqDto.getThemes())).thenReturn(new ArrayList<Facility>());
        when((rentalHomeFacilityRepository.saveAll(any(List.class))));

        //then
        verify(memberRepository, times(1)).findById(anyLong());
        verify(regionRepository, times(1)).findById(anyLong());
        verify(rentalHomeRepository, times(1)).save(any(RentalHome.class));
        verify(themeRepository, times(1)).findAllById(any(List.class));
        verify(rentalHomeThemeRepository, times(1)).saveAll(any(List.class));
        verify(facilityRepository, times(1)).findAllById(any(List.class));
        verify(rentalHomeFacilityRepository, times(1)).saveAll(any(List.class));
    }

    @Test
    void create() {
        //given

        //when

        //then
    }

    @Test
    void modifyRentalHome() {
        //given

        //when

        //then
    }

    @Test
    void deleteRentalHome() {
        //given

        //when

        //then
    }

    @Test
    void getRentalHomesByHost() {
        //given

        //when

        //then
    }

    @Test
    void getRentalHomeByHost() {
        //given

        //when

        //then
    }

    @Test
    void searchRentalHomesOrderByReview() {
        //given

        //when

        //then
    }

    @Test
    void searchRentalHomesOrderBySalesCount() {
        //given

        //when

        //then
    }
}