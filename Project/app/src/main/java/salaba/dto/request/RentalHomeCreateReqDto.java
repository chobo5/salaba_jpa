package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("숙소 등록 요청 DTO")
public class RentalHomeCreateReqDto {
    @ApiModelProperty("호스트(회원) id")
    private Long memberId;

    @ApiModelProperty("숙소명")
    private String name;

    @ApiModelProperty("숙소 상세 주소")
    private String street;

    @ApiModelProperty("숙소 우편번호")
    private Integer zipcode;

    @ApiModelProperty("최대 수용 인원")
    private Integer capacity;

    @ApiModelProperty("청소비")
    private Integer cleanFee;

    @ApiModelProperty("숙소 설명")
    private String explanation;

    @ApiModelProperty("숙소 위치 - 위도")
    private Double lat;

    @ApiModelProperty("숙소 위치 - 경도")
    private Double lon;

    @ApiModelProperty("숙소 1박 가격")
    private Integer price;

    @ApiModelProperty("숙소 이용 규칙")
    private String rule;

    @ApiModelProperty("숙소 지역 번호")
    private Long regionId;

    @ApiModelProperty("숙소 테마 번호")
    private List<Long> themes;

    @ApiModelProperty("숙소 시설 번호")
    private List<Long> facilities;
}
