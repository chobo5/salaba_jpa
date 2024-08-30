package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("비밀번호 변경 요청 DTO")
public class ChangePasswordReqDto {
    @ApiModelProperty("변경할 회원 id")
    private Long memberId;

    @ApiModelProperty("비밀번호")
    private String password;
}
