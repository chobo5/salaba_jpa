package salaba.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "회원 탈퇴 요청 DTO")
@AllArgsConstructor
@NoArgsConstructor
public class MemberResignReqDto {

    @Schema(description = "비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;

    @Schema(description = "리프레쉬 토큰")
    @NotEmpty
    private String refreshToken;
}
