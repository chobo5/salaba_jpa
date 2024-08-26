package salaba.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;
import salaba.entity.rental.Facility;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.RentalHomeStatus;
import salaba.entity.rental.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RentalHomeResDto {
    private Long rentalHomeId;

    private String name;

    private Address address;

    private Integer capacity;

    private Integer cleanFee;

    private Integer price;

    private String regionName;

    private RentalHomeStatus status;


    public RentalHomeResDto(RentalHome rentalHome) {
        rentalHomeId = rentalHome.getId();
        name = rentalHome.getName();
        address = rentalHome.getAddress();
        capacity = rentalHome.getCapacity();
        cleanFee = rentalHome.getCleanFee();
        price = rentalHome.getPrice();
        regionName = rentalHome.getRegion().getName();
        status = rentalHome.getStatus();
    }

}
