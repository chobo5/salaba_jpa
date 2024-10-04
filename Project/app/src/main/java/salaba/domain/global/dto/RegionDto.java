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
@Schema(description = "지역 DTO")
public class RegionDto {
    @Schema(description = "지역 id")
    @NotNull
    private Long id;

    @Schema(description = "지역명")
    @NotEmpty
    private String name;

    public RegionDto(Region region) {
        id = region.getId();
        name = region.getName();
    }
}
