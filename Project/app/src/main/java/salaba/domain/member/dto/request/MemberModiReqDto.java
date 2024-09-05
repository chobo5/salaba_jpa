package salaba.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.member.constants.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 기본 프로필 변경 요청 DTO")
public class MemberModiReqDto {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "국가 id")
    private Integer nationId;

    @Schema(description = "성별(MALE, FEMALE)")
    private Gender gender;

    @Schema(description = "상세 주소")
    private String street;

    @Schema(description = "우편 번호")
    private Integer zipcode;

}
