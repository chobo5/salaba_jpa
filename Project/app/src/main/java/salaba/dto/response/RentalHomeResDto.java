package salaba.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;
import salaba.entity.rental.RentalHome;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RentalHomeModifiedResponse {
    private Long rentalHomeId;

    private String name;

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

    public RentalHomeModifiedResponse(RentalHome rentalHome) {
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

        themes = rentalHome.getRentalHomeThemeSet().stream()
                .map(theme -> theme.getTheme().getName())
                .collect(Collectors.toList());

        facilities = rentalHome.getRentalHomeFacilitySet().stream()
                .map(facility -> facility.getFacility().getName())
                .collect(Collectors.toList());
    }
}
