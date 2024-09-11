package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Region;
import salaba.domain.common.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeModiReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.dto.response.ReviewResDto;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.rentalHome.repository.*;
import salaba.exception.NoAuthorityException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    public Page<ReviewResDto> getRentalHomeReviews(Long rentalHomeId, Pageable pageable) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        Page<Review> reviews = reviewService.findByRentalHome(rentalHome, pageable);
        return reviews.map(ReviewResDto::new);
    }

    public Double getRentalHomeReviewAvg(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        double reviewAvg = reviewService.getReviewAvg(rentalHome);
        return reviewAvg;
    }

    public Long createRentalHome(Long memberId, RentalHomeCreateReqDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);

        // 숙소 생성 후 저장
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, dto.getName(),
                dto.getExplanation(), new Address(dto.getStreet(), dto.getZipcode()), dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(),
                dto.getRule(), dto.getCleanFee());

        rentalHomeRepository.save(rentalHome);

        // 숙소_테마 저장
        List<Theme> themes = themeRepository.findAllById(dto.getThemes());
        List<RentalHomeTheme> rentalHomeThemes = themes.stream()
                .map(theme -> RentalHomeTheme.createRentalHomeTheme(rentalHome, theme))
                .collect(Collectors.toList());
        rentalHomeThemeRepository.saveAll(rentalHomeThemes);
        rentalHome.setThemes(rentalHomeThemes);

        // 숙소_시설 저장
        List<Facility> facilities = facilityRepository.findAllById(dto.getFacilities());
        List<RentalHomeFacility> rentalHomeFacilities = facilities.stream()
                .map(facility -> RentalHomeFacility.createRentalHomeFacility(rentalHome, facility))
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

        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);
        Address address = new Address(dto.getStreet(), dto.getZipcode());
        rentalHome.modifyRentalHome(region, dto.getName(), dto.getExplanation(), address, dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(), dto.getRule(), dto.getCleanFee());

        // 숙소_테마 저장
        List<Theme> themes = themeRepository.findAllById(dto.getThemes());
        List<RentalHomeTheme> rentalHomeThemes = themes.stream()
                .map(theme -> RentalHomeTheme.createRentalHomeTheme(rentalHome, theme))
                .collect(Collectors.toList());
        rentalHomeThemeRepository.saveAll(rentalHomeThemes);
        rentalHome.setThemes(rentalHomeThemes);

        // 숙소_시설 저장
        List<Facility> facilities = facilityRepository.findAllById(dto.getFacilities());
        List<RentalHomeFacility> rentalHomeFacilities = facilities.stream()
                .map(facility -> RentalHomeFacility.createRentalHomeFacility(rentalHome, facility))
                .collect(Collectors.toList());
        rentalHomeFacilityRepository.saveAll(rentalHomeFacilities);
        rentalHome.setFacilities(rentalHomeFacilities);

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
        Page<RentalHome> rentalHomes = rentalHomeRepository
                .findRentalHomesOrderByReview(regionName, themeName, minPrice, maxPrice, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }

    public Page<RentalHomeResDto> searchRentalHomesOrderBySalesCount(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable) {
        Page<RentalHome> rentalHomes = rentalHomeRepository
                .findRentalHomesOrderBySalesCount(regionName, themeName, minPrice, maxPrice, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }


}
