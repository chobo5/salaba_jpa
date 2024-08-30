package salaba.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import salaba.entity.ProcessStatus;
import salaba.entity.rental.PayMethod;
import salaba.entity.rental.Reservation;
import salaba.exception.CannotChangeStatusException;

import javax.persistence.*;
import java.util.List;

@Data
@ApiModel("결제 요청 DTO")
public class PaymentReqDto {
    @ApiModelProperty("예약 번호")
    private Long reservationId;

    @ApiModelProperty("결제 API 요청후 응답받은 결제번호")
    private String paymentId;

    @ApiModelProperty("최종 결제 금액")
    private int amount;

    @ApiModelProperty("결제 상태(AWAIT, COMPLETE, CANCEL, OVER)")
    private ProcessStatus status;

    @ApiModelProperty("결제 수단(CARD, ACCOUNT_TRANSFER, KAKAO, NAVER, TOSS)")
    private PayMethod method;

    @ApiModelProperty("할인 내역")
    private List<DiscountReqDto> discounts;


}
