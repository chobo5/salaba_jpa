package salaba.domain.rentalHome.dto.request;

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
    private String name;

    @Schema(description = "숙소 상세 주소")
    private String street;

    @Schema(description = "숙소 우편번호")
    private Integer zipcode;

    @Schema(description = "최대 수용 인원")
    private Integer capacity;

    @Schema(description = "청소비")
    private Integer cleanFee;

    @Schema(description = "숙소 설명")
    private String explanation;

    @Schema(description = "숙소 위치 - 위도")
    private Double lat;

    @Schema(description = "숙소 위치 - 경도")
    private Double lon;

    @Schema(description = "숙소 1박 가격")
    private Integer price;

    @Schema(description = "숙소 이용 규칙")
    private String rule;

    @Schema(description = "숙소 지역 번호")
    private Long regionId;

    @Schema(description = "숙소 테마 번호")
    private List<Long> themes;

    @Schema(description = "숙소 시설 번호")
    private List<Long> facilities;
}
