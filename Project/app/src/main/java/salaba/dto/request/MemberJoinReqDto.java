package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import salaba.util.Regex;
import salaba.util.RoleName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("회원 가입 요청 DTO")
public class MemberJoinReqDto {
    @ApiModelProperty("닉네임")
    @NotEmpty
    @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR)
    private String nickname;

    @ApiModelProperty("이름")
    @NotEmpty
    private String name;

    @ApiModelProperty("이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    private String email;

    @ApiModelProperty("비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;

    @ApiModelProperty("생일")
    @NotNull
    private LocalDate birthday;

}
