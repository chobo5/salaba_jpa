package salaba.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "로그인 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginReqDto {
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    @Schema(description = "이메일")
    private String email;

    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    @Schema(description = "비밀번호")
    private String password;
}
