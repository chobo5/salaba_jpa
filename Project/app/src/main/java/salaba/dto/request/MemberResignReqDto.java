package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("회원 탈퇴 요청 DTO")
public class MemberResignReqDto {
    @ApiModelProperty("이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    private String email;

    @ApiModelProperty("비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;
}
