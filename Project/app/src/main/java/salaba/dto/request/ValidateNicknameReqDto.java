package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("닉네임 유효성 검사 DTO")
public class ValidateNicknameReqDto {

    @Parameter(name = "nickname")
    @NotEmpty
    @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR)
    @ApiModelProperty("닉네임(소문자, 대문자, 숫자, 언더스코어(_)만 포함, 4자에서 20자 사이의 길이)")
    private String nickname;
}
