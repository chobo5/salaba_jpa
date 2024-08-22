package salaba.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationReqDto {
    private Long memberId;
    private Long rentalHomeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
