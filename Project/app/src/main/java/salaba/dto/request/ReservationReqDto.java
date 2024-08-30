package salaba.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("숙소 예약 요청 DTO")
public class ReservationReqDto {
    @ApiModelProperty("회원 id")
    private Long memberId;

    @ApiModelProperty("숙소 id")
    private Long rentalHomeId;

    @ApiModelProperty("예약 시작일")
    private LocalDateTime startDate;

    @ApiModelProperty("예약 종료일")
    private LocalDateTime endDate;
}
