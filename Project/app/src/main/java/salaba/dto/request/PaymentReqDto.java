package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import salaba.util.ProcessStatus;
import salaba.entity.rental.PayMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("결제 요청 DTO")
public class PaymentReqDto {
    @ApiModelProperty("예약 번호")
    @NotNull
    private Long reservationId;

    @ApiModelProperty("결제 API 요청후 응답받은 결제번호")
    @NotEmpty
    private String paymentId;

    @ApiModelProperty("최종 결제 금액")
    @NotNull
    private int amount;

    @ApiModelProperty("결제 상태(AWAIT, COMPLETE, CANCEL, OVER)")
    @NotNull
    private ProcessStatus status;

    @ApiModelProperty("결제 수단(CARD, ACCOUNT_TRANSFER, KAKAO, NAVER, TOSS)")
    @NotNull
    private PayMethod method;

    @ApiModelProperty("할인 내역")
    private List<DiscountReqDto> discounts;


}
