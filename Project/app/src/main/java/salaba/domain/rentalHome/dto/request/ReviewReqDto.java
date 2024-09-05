package salaba.domain.rentalHome.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "숙소 리뷰 작성 DTO")
public class ReviewReqDto {
    @Schema(description = "예약 번호")
    @NotNull
    private Long reservationId;

    @Schema(description = "내용")
    @NotEmpty
    private String content;

    @Schema(description = "평점(1 ~ 5점)")
    @NotNull
    private Integer score;
}
