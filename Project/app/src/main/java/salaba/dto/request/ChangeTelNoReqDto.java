package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("연락처 변경 요청 DTO")
public class ChangeTelNoReqDto {
    @ApiModelProperty("변경할 회원 id")
    private Long memberId;

    @ApiModelProperty("연락처")
    private String telNo;
}
