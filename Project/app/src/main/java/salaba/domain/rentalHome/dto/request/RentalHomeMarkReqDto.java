package salaba.domain.rentalHome.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "숙소 찜 요청 DTO")
public class RentalHomeMarkReqDto {
    @Schema(description = "숙소 Id")
    @NotNull
    private Long rentalHomeId;

}
