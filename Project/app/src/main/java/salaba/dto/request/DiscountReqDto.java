package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("숙소 할인 요청 DTO")
public class DiscountReqDto {

    @ApiModelProperty("금액")
    private int amount;
    @ApiModelProperty("할인 내용")
    private String content;
}
