package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("회원 기본 프로필 변경 요청 DTO")
public class MemberModiReqDto {
    @ApiModelProperty("변경할 회원 id")
    private Long memberId;

    @ApiModelProperty("이름")
    private String name;

    @ApiModelProperty("국가 id")
    private Integer nationId;

    @ApiModelProperty("성별(MALE, FEMALE)")
    private Gender gender;

    @ApiModelProperty("상세 주소")
    private String street;

    @ApiModelProperty("우편 번호")
    private Integer zipcode;

}
