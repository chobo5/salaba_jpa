package salaba.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙소 예약 요청 DTO")
public class ReservationReqDto {

    @Schema(description = "숙소 id")
    @NotNull
    private Long rentalHomeId;

    @Schema(description = "예약 시작일")
    @NotNull
    private LocalDateTime startDate;

    @Schema(description = "예약 종료일")
    @NotNull
    private LocalDateTime endDate;
}
