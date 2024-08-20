package salaba.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;

import java.util.List;

@Data
@NoArgsConstructor
public class RentalHomeDetailReqDto {
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
    private String region;

    private List<String> themes;
    private List<String> facilities;
}
