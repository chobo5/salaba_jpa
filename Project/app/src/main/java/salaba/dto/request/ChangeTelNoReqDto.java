package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "연락처 변경 요청 DTO")
public class ChangeTelNoReqDto {
    @Schema(description = "변경할 회원 id")
    @NotNull
    private Long memberId;

    @Schema(description = "연락처")
    @NotEmpty
    private String telNo;
}
