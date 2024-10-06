package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.rentalHome.entity.Facility;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.RentalHomeFacility;
import salaba.domain.rentalHome.entity.RentalHomeTheme;
import salaba.domain.rentalHome.repository.FacilityRepository;
import salaba.domain.rentalHome.repository.RentalHomeFacilityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalHomeFacilityService {
    private final FacilityRepository facilityRepository;
    private final RentalHomeFacilityRepository rentalHomeFacilityRepository;

    public List<RentalHomeFacility> saveAll(RentalHome rentalHome, List<Long> facilityIds) {
        List<Facility> facilities = facilityRepository.findAllById(facilityIds);
        List<RentalHomeFacility> rentalHomeFacilities = facilities.stream()
                .map(facility -> RentalHomeFacility.create(rentalHome, facility))
                .collect(Collectors.toList());
        return rentalHomeFacilityRepository.saveAll(rentalHomeFacilities);
    }

    public void deleteAll(RentalHome rentalHome) {
        rentalHomeFacilityRepository.deleteByRentalHome(rentalHome);
    }


}
