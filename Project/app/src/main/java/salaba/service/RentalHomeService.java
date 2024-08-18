package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.RentalHomeCreateDto;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.entity.rental.*;
import salaba.repository.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalHomeService {
    private final RentalHomeRepository rentalHomeRepository;
    private final MemberRepository memberRepository;
    private final RegionRepsitory regionRepsitory;
    private final FacilityRepository facilityRepository;
    private final ThemeRepository themeRepository;
    private final RentalHomeFacilityRepository rentalHomeFacilityRepository;
    private final RentalHomeThemeRepository rentalHomeThemeRepository;

    public Long createRentalHome(RentalHomeCreateDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Region region = regionRepsitory.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);

        // 숙소 생성 후 저장
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, dto.getName(),
                dto.getExplanation(), dto.getAddress(), dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(),
                dto.getHostingStartDate(), dto.getHostingEndDate(), dto.getRule(), dto.getCleanFee());

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
}
