package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("닉네임 변경 요청 DTO")
public class ChangeNicknameReqDto {

    @ApiModelProperty("변경할 회원 id")
    @NotNull
    private Long memberId;

    @ApiModelProperty("닉네임")
    @NotEmpty
    @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR)
    private String nickname;
}
