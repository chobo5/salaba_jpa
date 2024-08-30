package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("숙소 리뷰 작성 DTO")
public class ReviewReqDto {
    @ApiModelProperty("예약 번호")
    private Long reservationId;

    @ApiModelProperty("내용")
    private String content;

    @ApiModelProperty("평점(1 ~ 5점)")
    private int score;
}
