package salaba.dto.request;

import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ValidateEmailReqDto {
    @NotEmpty
    @Email(message = Regex.EMAIL_ERROR)
    private String email;
}
