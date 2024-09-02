package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel("국가 DTO")
public class NationDto {
    @ApiModelProperty("국가 번호")
    @NotNull
    private Integer id;

    @ApiModelProperty("국가명")
    @NotEmpty
    private String name;
}
