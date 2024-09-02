package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("숙소 정보 수정 요청 DTO")
public class RentalHomeModiReqDto {
    @ApiModelProperty("숙소 id")
    @NotNull
    private Long rentalHomeId;

    @ApiModelProperty("숙소명")
    @NotEmpty
    private String name;

    @ApiModelProperty("숙소 상세 주소")
    @NotEmpty
    private String street;

    @ApiModelProperty("숙소 우편번호")
    @NotNull
    private Integer zipcode;

    @ApiModelProperty("최대 수용 인원")
    @NotNull
    private Integer capacity;

    @ApiModelProperty("청소비")
    @NotNull
    private Integer cleanFee;

    @ApiModelProperty("숙소 설명")
    @NotEmpty
    private String explanation;

    @ApiModelProperty("숙소 위치 - 위도")
    @NotNull
    private Double lat;

    @ApiModelProperty("숙소 위치 - 경도")
    @NotNull
    private Double lon;

    @ApiModelProperty("숙소 1박 가격")
    @NotNull
    private Integer price;

    @ApiModelProperty("숙소 이용 규칙")
    private String rule;

    @ApiModelProperty("숙소 지역 번호")
    @NotNull
    private Long regionId;

    @ApiModelProperty("숙소 테마 번호")
    @NotEmpty
    private List<Long> themes;

    @ApiModelProperty("숙소 시설 번호")
    @NotEmpty
    private List<Long> facilities;
}
