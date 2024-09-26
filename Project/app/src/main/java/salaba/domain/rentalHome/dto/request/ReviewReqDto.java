package salaba.domain.rentalHome.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "숙소 리뷰 작성 DTO")
@NoArgsConstructor
@AllArgsConstructor
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
