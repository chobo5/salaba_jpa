package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("숙소 할인 요청 DTO")
public class DiscountReqDto {

    @ApiModelProperty("금액")
    @NotNull
    private Integer amount;

    @ApiModelProperty("할인 내용")
    @NotEmpty
    private String content;
}
