package salaba.domain.rentalHome.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.common.entity.Address;
import salaba.domain.rentalHome.entity.Facility;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RentalHomeDetailResDto {
    private Long rentalHomeId;

    private String name;

    private String hostName;

    private String hostTel;

    private Address address;

    private Integer capacity;

    private Integer cleanFee;

    private String explanation;

    private Double lat;

    private Double lon;

    private Integer price;

    private String rule;

    private String regionName;

    private List<String> themes;

    private List<String> facilities;

    public RentalHomeDetailResDto(RentalHome rentalHome) {
        rentalHomeId = rentalHome.getId();
        name = rentalHome.getName();
        address = rentalHome.getAddress();
        capacity = rentalHome.getCapacity();
        cleanFee = rentalHome.getCleanFee();
        explanation = rentalHome.getExplanation();
        lat = rentalHome.getLat();
        lon = rentalHome.getLon();
        price = rentalHome.getPrice();
        rule = rentalHome.getRule();
        regionName = rentalHome.getRegion().getName();

        themes = rentalHome.getRentalHomeThemes().stream()
                .map(theme -> theme.getTheme().getName())
                .collect(Collectors.toList());

        facilities = rentalHome.getRentalHomeFacilities().stream()
                .map(facility -> facility.getFacility().getName())
                .collect(Collectors.toList());
    }

    public RentalHomeDetailResDto(RentalHome rentalHome, List<Theme> themes, List<Facility> facilities) {
        rentalHomeId = rentalHome.getId();
        name = rentalHome.getName();
        hostName = rentalHome.getHost().getName();
        hostTel = rentalHome.getHost().getTelNo();
        address = rentalHome.getAddress();
        capacity = rentalHome.getCapacity();
        cleanFee = rentalHome.getCleanFee();
        explanation = rentalHome.getExplanation();
        lat = rentalHome.getLat();
        lon = rentalHome.getLon();
        price = rentalHome.getPrice();
        rule = rentalHome.getRule();
        regionName = rentalHome.getRegion().getName();

        this.themes = themes.stream()
                .map(Theme::getName)
                .collect(Collectors.toList());

        this.facilities = facilities.stream()
                .map(Facility::getName)
                .collect(Collectors.toList());
    }
}
