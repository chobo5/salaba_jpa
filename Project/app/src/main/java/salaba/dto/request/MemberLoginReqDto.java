package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("로그인 요청 DTO")
public class MemberLoginReqDto {
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    @ApiModelProperty("이메일")
    private String email;

    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    @ApiModelProperty("비밀번호")
    private String password;
}
