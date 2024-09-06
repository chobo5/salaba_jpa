package salaba.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import salaba.domain.common.constants.ProcessStatus;
import salaba.domain.reservation.constants.PayMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "결제 요청 DTO")
public class PaymentReqDto {
    @Schema(description = "예약 번호")
    @NotNull
    private Long reservationId;

    @Schema(description = "결제 API 요청후 응답받은 결제코드")
    @NotEmpty
    private String paymentCode;

    @Schema(description = "최종 결제 금액")
    @NotNull
    private int amount;

    @Schema(description = "결제 상태(AWAIT, COMPLETE, CANCEL, OVER)")
    @NotNull
    private ProcessStatus status;

    @Schema(description = "결제 수단(CARD, ACCOUNT_TRANSFER, KAKAO, NAVER, TOSS)")
    @NotNull
    private PayMethod method;

    @Schema(description = "할인 내역")
    private List<DiscountReqDto> discounts;


}
