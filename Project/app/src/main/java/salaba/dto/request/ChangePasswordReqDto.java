package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "비밀번호 변경 요청 DTO")
public class ChangePasswordReqDto {
    @Schema(description = "변경할 회원 id")
    @NotNull
    private Long memberId;

    @Schema(description = "비밀번호")
    @NotEmpty
    @Pattern(regexp = Regex.PASSWORD, message = Regex.PASSWORD_ERROR)
    private String password;
}
