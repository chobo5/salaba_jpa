package salaba.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;
import salaba.entity.rental.Facility;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RentalHomeResDto {
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

    public RentalHomeResDto(RentalHome rentalHome) {
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

    public RentalHomeResDto(RentalHome rentalHome, List<Theme> themes, List<Facility> facilities) {
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
