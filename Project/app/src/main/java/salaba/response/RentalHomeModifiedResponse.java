package salaba.response;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime hostingStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime hostingEndDate;

    private Double lat;

    private Double lon;

    private Integer price;

    private String rule;

    private Long regionId;

    private List<String> themes;

    private List<String> facilities;

    public RentalHomeModifiedResponse(RentalHome rentalHome) {
        rentalHomeId = rentalHome.getId();
        name = rentalHome.getName();
        address = rentalHome.getAddress();
        capacity = rentalHome.getCapacity();
        cleanFee = rentalHome.getCleanFee();
        explanation = rentalHome.getExplanation();
        hostingStartDate = rentalHome.getHostingStartDate();
        hostingEndDate = rentalHome.getHostingEndDate();
        lat = rentalHome.getLat();
        lon = rentalHome.getLon();
        price = rentalHome.getPrice();
        rule = rentalHome.getRule();
        regionId = rentalHome.getRegion().getId();

        themes = rentalHome.getRentalHomeThemeSet().stream()
                .map(theme -> theme.getTheme().getName())
                .collect(Collectors.toList());

        facilities = rentalHome.getRentalHomeFacilitySet().stream()
                .map(facility -> facility.getFacility().getName())
                .collect(Collectors.toList());
    }
}
