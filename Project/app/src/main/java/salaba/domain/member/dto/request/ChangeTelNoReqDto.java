package salaba.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "연락처 변경 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTelNoReqDto {
    @Schema(description = "연락처")
    @NotEmpty
    private String telNo;
}
