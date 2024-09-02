package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("숙소 리뷰 작성 DTO")
public class ReviewReqDto {
    @ApiModelProperty("예약 번호")
    @NotNull
    private Long reservationId;

    @ApiModelProperty("내용")
    @NotEmpty
    private String content;

    @ApiModelProperty("평점(1 ~ 5점)")
    @NotNull
    private Integer score;
}
