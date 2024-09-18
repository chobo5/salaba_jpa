package salaba.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "비밀번호 변경 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordReqDto {
    @Schema(description = "기존 비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;

    @Schema(description = "새로운 비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String newPassword;
}
