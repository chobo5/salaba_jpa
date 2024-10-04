package salaba.domain.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.global.entity.Region;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "지역 생성 DTO")
public class RegionCreateDto {
    @Schema(description = "국가 id")
    @NotNull
    private Integer nationId;

    @Schema(description = "지역명")
    @NotEmpty
    private String name;

}
