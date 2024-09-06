package salaba.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "숙소 할인 요청 DTO")
public class DiscountReqDto {
    @Schema(description = "예약 id")
    @NotNull
    private Long reservationId;

    @Schema(description = "금액")
    @NotNull
    private Integer amount;

    @Schema(description = "할인 내용")
    @NotEmpty
    private String content;
}
