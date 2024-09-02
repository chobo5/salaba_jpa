package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("비밀번호 변경 요청 DTO")
public class ChangePasswordReqDto {
    @ApiModelProperty("변경할 회원 id")
    @NotNull
    private Long memberId;

    @ApiModelProperty("비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;
}
