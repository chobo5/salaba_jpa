package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "회원 탈퇴 요청 DTO")
public class MemberResignReqDto {
    @Schema(description = "이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    private String email;

    @Schema(description = "비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;
}
