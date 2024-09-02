package salaba.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "숙소 정보 수정 요청 DTO")
public class RentalHomeModiReqDto {
    @Schema(description = "숙소 id")
    @NotNull
    private Long rentalHomeId;

    @Schema(description = "숙소명")
    @NotEmpty
    private String name;

    @Schema(description = "숙소 상세 주소")
    @NotEmpty
    private String street;

    @Schema(description = "숙소 우편번호")
    @NotNull
    private Integer zipcode;

    @Schema(description = "최대 수용 인원")
    @NotNull
    private Integer capacity;

    @Schema(description = "청소비")
    @NotNull
    private Integer cleanFee;

    @Schema(description = "숙소 설명")
    @NotEmpty
    private String explanation;

    @Schema(description = "숙소 위치 - 위도")
    @NotNull
    private Double lat;

    @Schema(description = "숙소 위치 - 경도")
    @NotNull
    private Double lon;

    @Schema(description = "숙소 1박 가격")
    @NotNull
    private Integer price;

    @Schema(description = "숙소 이용 규칙")
    private String rule;

    @Schema(description = "숙소 지역 번호")
    @NotNull
    private Long regionId;

    @Schema(description = "숙소 테마 번호")
    @NotEmpty
    private List<Long> themes;

    @Schema(description = "숙소 시설 번호")
    @NotEmpty
    private List<Long> facilities;
}
