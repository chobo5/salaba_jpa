package salaba.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RentalHomeCreateDto {
    private Long memberId;
    private String name;
    private Address address;
    private Integer capacity;
    private Integer cleanFee;
    private String explanation;
    private LocalDateTime hostingStartDate;
    private LocalDateTime hostingEndDate;
    private Double lat;
    private Double lon;
    private Integer price;
    private String rule;
    private Long regionId;
}
