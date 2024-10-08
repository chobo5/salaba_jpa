package salaba.domain.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.global.entity.Nation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "국가 DTO")
public class NationDto {
    @Schema(description = "국가 id")
    @NotNull
    private Integer id;

    @Schema(description = "국가명")
    @NotEmpty
    private String name;

    public NationDto(Nation nation) {
        id = nation.getId();
        name = nation.getName();
    }
}
