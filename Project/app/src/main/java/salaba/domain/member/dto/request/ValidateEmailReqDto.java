package salaba.domain.member.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "이메일 유효성 검사 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class ValidateEmailReqDto {
    @Parameter(name = "이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    @Schema(description = "이메일")
    private String email;
}
