package salaba.domain.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import salaba.util.Regex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "닉네임 변경 요청 DTO")
public class ChangeNicknameReqDto {

    @Schema(description = "닉네임")
    @NotEmpty
    @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR)
    private String nickname;
}
