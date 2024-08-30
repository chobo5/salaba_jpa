package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("회원 탈퇴 요청 DTO")
public class MemberResignReqDto {
    @ApiModelProperty("이메일")
    private String email;

    @ApiModelProperty("비밀번호")
    private String password;
}
