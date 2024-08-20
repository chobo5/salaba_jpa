package salaba.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RentalHomeModifyDto {
    private Long rentalHomeId;
    private String name;
    private String street;
    private Integer zipcode;
    private Integer capacity;
    private Integer cleanFee;
    private String explanation;
    private Double lat;
    private Double lon;
    private Integer price;
    private String rule;
    private Long regionId;

    private List<Long> themes;
    private List<Long> facilities;
}
