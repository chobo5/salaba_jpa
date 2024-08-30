package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("닉네임 변경 요청 DTO")
public class ChangeNicknameReqDto {
    @ApiModelProperty("변경할 회원 id")
    private Long memberId;

    @ApiModelProperty("닉네임")
    private String nickname;
}
