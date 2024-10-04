package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Region;
import salaba.domain.global.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeModiReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.rentalHome.repository.*;
import salaba.domain.auth.exception.NoAuthorityException;
import salaba.domain.reservation.service.ReviewService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalHomeService {
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final RegionRepository regionRepository;
    private final FacilityRepository facilityRepository;
    private final ThemeRepository themeRepository;
    private final RentalHomeFacilityRepository rentalHomeFacilityRepository;
    private final RentalHomeThemeRepository rentalHomeThemeRepository;
    private final ReviewService reviewService;

    public RentalHomeDetailResDto getRentalHome(Long rentalHomeId) {
        return rentalHomeRepository.findDetailById(rentalHomeId);
    }

    public Long createRentalHome(Long memberId, RentalHomeCreateReqDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);

        // 숙소 생성 후 저장
        RentalHome rentalHome = RentalHome.create(member, region, dto.getName(),
                dto.getExplanation(), new Address(dto.getStreet(), dto.getZipcode()), dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(), dto.getRule(), dto.getCleanFee());

        rentalHomeRepository.save(rentalHome);

        // 숙소_테마 저장
        List<Theme> themes = themeRepository.findAllById(dto.getThemes());
        List<RentalHomeTheme> rentalHomeThemes = themes.stream()
                .map(theme -> RentalHomeTheme.create(rentalHome, theme))
                .collect(Collectors.toList());
        rentalHomeThemeRepository.saveAll(rentalHomeThemes);
        rentalHome.setThemes(rentalHomeThemes);

        // 숙소_시설 저장
        List<Facility> facilities = facilityRepository.findAllById(dto.getFacilities());
        List<RentalHomeFacility> rentalHomeFacilities = facilities.stream()
                .map(facility -> RentalHomeFacility.create(rentalHome, facility))
                .collect(Collectors.toList());
        rentalHomeFacilityRepository.saveAll(rentalHomeFacilities);
        rentalHome.setFacilities(rentalHomeFacilities);
        return rentalHome.getId();
    }

    public RentalHomeDetailResDto modifyRentalHome(Long memberId, RentalHomeModiReqDto dto) {
        //숙소의 호스트를 찾는다.
        Member host = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        //숙소의 id와 호스트를 통해 숙소를 찾는다.
        RentalHome rentalHome = rentalHomeRepository.findByIdAndHost(dto.getRentalHomeId(), host)
                .orElseThrow(NoSuchElementException::new);

        Region region = dto.getRegionId() != null ?
                regionRepository.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new) : null;

        Address address = dto.getStreet() != null && dto.getZipcode() != null ?
                new Address(dto.getStreet(), dto.getZipcode()) : null;

        rentalHome.modify(region, dto.getName(), dto.getExplanation(), address, dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(), dto.getRule(), dto.getCleanFee());

        // 숙소_테마 저장
        if (dto.getThemes() != null && !dto.getThemes().isEmpty()) {
            List<Theme> themes = themeRepository.findAllById(dto.getThemes());
            rentalHomeThemeRepository.deleteByRentalHome(rentalHome);
            List<RentalHomeTheme> rentalHomeThemes = themes.stream()
                    .map(theme -> RentalHomeTheme.create(rentalHome, theme))
                    .collect(Collectors.toList());
            rentalHomeThemeRepository.saveAll(rentalHomeThemes);
            rentalHome.setThemes(rentalHomeThemes);
        }


        // 숙소_시설 저장
        if (dto.getFacilities() != null && !dto.getFacilities().isEmpty()) {
            List<Facility> facilities = facilityRepository.findAllById(dto.getFacilities());
            rentalHomeFacilityRepository.deleteByRentalHome(rentalHome);
            List<RentalHomeFacility> rentalHomeFacilities = facilities.stream()
                    .map(facility -> RentalHomeFacility.create(rentalHome, facility))
                    .collect(Collectors.toList());
            rentalHomeFacilityRepository.saveAll(rentalHomeFacilities);
            rentalHome.setFacilities(rentalHomeFacilities);
        }


        return new RentalHomeDetailResDto(rentalHome);
    }

    public Long deleteRentalHome(Long memberId, Long rentalHomeId) {
        Member host = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findWithReservations(rentalHomeId).orElseThrow(NoSuchElementException::new);
        if (!rentalHome.getHost().equals(host)) {
            throw new NoAuthorityException("숙소 삭제 권한이 없습니다.");
        }
        rentalHome.closeRentalHome();
        return rentalHome.getId();
    }

    public Page<RentalHomeResDto> getRentalHomesByHost(Long hostId, Pageable pageable) {
        Page<RentalHome> rentalHomes = rentalHomeRepository.findByHost(hostId, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }

    public RentalHomeDetailResDto getRentalHomeByHost(Long memberId, Long rentalHomeId) {
        return rentalHomeRepository.findDetailByIdAndHost(rentalHomeId, memberId);
    }

    public Page<RentalHomeResDto> searchRentalHomesOrderByReview(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {
//        Page<RentalHome> rentalHomes = rentalHomeRepository
//                .findRentalHomesOrderByReview(regionName, themeName, minPrice, maxPrice, pageable);
//
//        return rentalHomes.map(RentalHomeResDto::new);
        return rentalHomeRepository.findRentalHomeDtosOrderByReview(regionName, themeName, minPrice, maxPrice, pageable);
    }

    public Page<RentalHomeResDto> searchRentalHomesOrderBySalesCount(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {
        Page<RentalHome> rentalHomes = rentalHomeRepository
                .findRentalHomesOrderBySalesCount(regionName, themeName, minPrice, maxPrice, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }



}
