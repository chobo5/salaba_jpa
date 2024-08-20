package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.entity.Address;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.entity.rental.*;
import salaba.repository.*;
import salaba.repository.rentalHome.*;
import salaba.dto.response.RentalHomeResDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalHomeService {
    private final RentalHomeRepository rentalHomeRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final FacilityRepository facilityRepository;
    private final ThemeRepository themeRepository;
    private final RentalHomeFacilityRepository rentalHomeFacilityRepository;
    private final RentalHomeThemeRepository rentalHomeThemeRepository;

    public Long createRentalHome(RentalHomeCreateReqDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(NoSuchElementException::new);
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

    public RentalHomeResDto modifyRentalHome(RentalHomeModiReqDto dto) {
        RentalHome rentalHome = rentalHomeRepository.findById(dto.getRentalHomeId()).orElseThrow(NoSuchElementException::new);
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);
        rentalHome.modifyRentalHome(region, dto.getName(), dto.getExplanation(),
                new Address(dto.getStreet(), dto.getZipcode()), dto.getPrice(),
                dto.getCapacity(), dto.getLat(),
                dto.getLon(), dto.getRule(), dto.getCleanFee());

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

        return new RentalHomeResDto(rentalHome);
    }

    public RentalHomeResDto get(Long rentalHomeId) {
        return rentalHomeRepository.get(rentalHomeId);
    }
}
