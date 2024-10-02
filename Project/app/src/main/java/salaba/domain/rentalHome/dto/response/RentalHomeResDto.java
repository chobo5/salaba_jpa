package salaba.domain.rentalHome.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.global.entity.Address;
import salaba.domain.rentalHome.constants.RentalHomeStatus;
import salaba.domain.rentalHome.entity.RentalHome;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalHomeResDto {
    private Long rentalHomeId;

    private String name;

    private Address address;

    private Integer capacity;

    private Integer cleanFee;

    private Integer price;

    private String regionName;

    private RentalHomeStatus status;

    private long reviewCount;

    private double reviewAvg;


    public RentalHomeResDto(RentalHome rentalHome) {
        rentalHomeId = rentalHome.getId();
        name = rentalHome.getName();
        address = rentalHome.getAddress();
        capacity = rentalHome.getCapacity();
        cleanFee = rentalHome.getCleanFee();
        price = rentalHome.getPrice();
        regionName = rentalHome.getRegion().getName();
        status = rentalHome.getStatus();
        reviewCount = rentalHome.getReviewCount();
        reviewAvg = rentalHome.getReviewAvg();

    }

}
