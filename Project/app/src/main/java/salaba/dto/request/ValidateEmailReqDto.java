package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("이메일 유효성 검사 DTO")
public class ValidateEmailReqDto {
    @Parameter(name = "이메일")
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    @ApiModelProperty("이메일")
    private String email;
}
