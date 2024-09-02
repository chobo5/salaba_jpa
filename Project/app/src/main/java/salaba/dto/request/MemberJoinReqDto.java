package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "회원 가입 요청 DTO")
public class MemberJoinReqDto {
    @Schema(description = "닉네임")
    @NotEmpty
    @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR)
    private String nickname;

    @Schema(description = "이름")
    @NotEmpty
    private String name;

    @Schema(description = "이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    private String email;

    @Schema(description = "비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;

    @Schema(description = "생일")
    @NotNull
    private LocalDate birthday;

}
