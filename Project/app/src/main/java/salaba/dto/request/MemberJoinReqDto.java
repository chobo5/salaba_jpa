package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("회원 가입 요청 DTO")
public class MemberJoinReqDto {
    @ApiModelProperty("닉네임")
    private String nickname;

    @ApiModelProperty("이름")
    private String name;

    @ApiModelProperty("이메일")
    private String email;

    @ApiModelProperty("비밀번호")
    private String password;

    @ApiModelProperty("생일")
    private LocalDate birthday;

}
