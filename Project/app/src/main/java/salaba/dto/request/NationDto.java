package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "국가 DTO")
public class NationDto {
    @Schema(description = "국가 번호")
    @NotNull
    private Integer id;

    @Schema(description = "국가명")
    @NotEmpty
    private String name;
}
