package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("연락처 변경 요청 DTO")
public class ChangeTelNoReqDto {
    @ApiModelProperty("변경할 회원 id")
    @NotNull
    private Long memberId;

    @ApiModelProperty("연락처")
    @NotEmpty
    private String telNo;
}
