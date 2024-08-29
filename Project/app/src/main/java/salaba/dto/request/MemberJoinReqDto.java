package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원 가입 요청 DTO")
public class MemberJoinReqDto {
    @ApiModelProperty(value = "닉네임", required = true)
    private String nickname;

    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;

    @ApiModelProperty(value = "생일", required = true)
    private LocalDate birthday;

}
