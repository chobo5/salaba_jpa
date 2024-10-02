package salaba.domain.rentalHome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import salaba.global.entity.Address;
import salaba.global.entity.Nation;
import salaba.global.entity.Region;
import salaba.global.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeModiReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.rentalHome.repository.*;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.rentalHome.exception.CannotChangeStatusException;
import salaba.domain.auth.exception.NoAuthorityException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Long memberId = 1L;
        RentalHomeCreateReqDto reqDto = new RentalHomeCreateReqDto("testHome", "testStreet", 123123, 4, 10000, "testtest",
                123.12321, 232.312313, 100000, "rule", 1L, Arrays.asList(1L), Arrays.asList(1L));

        //when
        Member member = Member.create("test@test.com", "Aabcd1231312", "testName",
                "testNickname", LocalDate.of(1999, 10, 10));
        Region region = new Region("제주", new Nation(82, "korea"));
        RentalHome rentalHome = RentalHome.create(member, region, reqDto.getName(),
                reqDto.getExplanation(), new Address(reqDto.getStreet(), reqDto.getZipcode()), reqDto.getPrice(),
                reqDto.getCapacity(), reqDto.getLat(), reqDto.getLon(), reqDto.getRule(), reqDto.getCleanFee());
        List<Theme> themes = Arrays.asList(new Theme("theme1"));
        List<Facility> facilities = Arrays.asList(new Facility("facility"));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(regionRepository.findById(reqDto.getRegionId())).thenReturn(Optional.of(region));
        when(themeRepository.findAllById(reqDto.getThemes())).thenReturn(themes);
        when(facilityRepository.findAllById(reqDto.getFacilities())).thenReturn(facilities);

        rentalHomeService.createRentalHome(memberId, reqDto);
//
//        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(regionRepository, times(1)).findById(reqDto.getRegionId());
        verify(rentalHomeRepository, times(1)).save(any(RentalHome.class));
        verify(themeRepository, times(1)).findAllById(reqDto.getThemes());
        verify(rentalHomeThemeRepository, times(1)).saveAll(anyIterable());
        verify(facilityRepository, times(1)).findAllById(reqDto.getFacilities());
        verify(rentalHomeFacilityRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void 숙소수정() {
        //given
        Long memberId = 1L;
        Long rentalHomeId = 1L;
        RentalHomeModiReqDto reqDto = new RentalHomeModiReqDto(rentalHomeId, "testhome2", null, null,
                null, null, null, null, null, null, null,
                null,  Arrays.asList(1L, 2L), Arrays.asList(1L, 2L));

        //when
        Member member = Member.create("test@test.com", "Aabcd1231312", "testName",
                "testNickname", LocalDate.of(1999, 10, 10));
        Region region = new Region("제주", new Nation(82, "korea"));
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        List<Theme> themes = Arrays.asList(new Theme("theme1"), new Theme("theme2"));
        List<Facility> facilities = Arrays.asList(new Facility("facility1"), new Facility("facility2"));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(rentalHomeRepository.findByIdAndHost(rentalHomeId, member)).thenReturn(Optional.of(rentalHome));
        when(themeRepository.findAllById(reqDto.getThemes())).thenReturn(themes);
        when(facilityRepository.findAllById(reqDto.getFacilities())).thenReturn(facilities);

        rentalHomeService.modifyRentalHome(memberId, reqDto);
//
//        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(regionRepository, times(0)).findById(reqDto.getRegionId());
        verify(themeRepository, times(1)).findAllById(reqDto.getThemes());
        verify(rentalHomeThemeRepository, times(1)).deleteByRentalHome(any(RentalHome.class));
        verify(rentalHomeThemeRepository, times(1)).saveAll(anyIterable());
        verify(facilityRepository, times(1)).findAllById(reqDto.getFacilities());
        verify(rentalHomeFacilityRepository, times(1)).deleteByRentalHome(any(RentalHome.class));
        verify(rentalHomeFacilityRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void 숙소폐쇄() {
        //given
        Long memberId = 1L;
        Long rentalHomeId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(rentalHomeRepository.findWithReservations(rentalHomeId)).thenReturn(Optional.of(rentalHome));

        rentalHomeService.deleteRentalHome(memberId, rentalHomeId);
        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(rentalHomeRepository, times(1)).findWithReservations(rentalHomeId);

    }

    @Test
    void 숙소폐쇄_실패_회원권한없음() {
        //given
        Long memberId = 1L;
        Long rentalHomeId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Member otherMember = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(otherMember));
        when(rentalHomeRepository.findWithReservations(rentalHomeId)).thenReturn(Optional.of(rentalHome));

        assertThrows(NoAuthorityException.class, () -> rentalHomeService.deleteRentalHome(memberId, rentalHomeId));

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(rentalHomeRepository, times(1)).findWithReservations(rentalHomeId);

    }

    @Test
    void 숙소폐쇄_실패_남은예약있음() {
        //given
        Long memberId = 1L;
        Long rentalHomeId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Member otherMember = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));

        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);

        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.now().plusDays(1), rentalHome, otherMember);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(rentalHomeRepository.findWithReservations(rentalHomeId)).thenReturn(Optional.of(rentalHome));

        assertThrows(CannotChangeStatusException.class, () -> rentalHomeService.deleteRentalHome(memberId, rentalHomeId));
        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(rentalHomeRepository, times(1)).findWithReservations(rentalHomeId);

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