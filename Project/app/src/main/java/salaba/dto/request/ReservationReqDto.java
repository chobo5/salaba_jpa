package salaba.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationReqDto {
    private Long memberId;
    private Long rentalHomeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
