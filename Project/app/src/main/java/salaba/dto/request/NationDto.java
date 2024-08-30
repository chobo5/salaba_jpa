package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@ApiModel("국가 DTO")
public class NationDto {
    @ApiModelProperty("국가 번호")
    private Integer id;

    @ApiModelProperty("국가명")
    private String name;
}
