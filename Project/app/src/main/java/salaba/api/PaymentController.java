package salaba.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.request.PaymentReqDto;
import salaba.service.PaymentService;
import salaba.util.MemberContextHolder;
import salaba.util.RestResult;
@Tag(name = "결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 완료")
    @PostMapping("complete")
    public RestResult<?> recordPayment(@RequestBody PaymentReqDto reqDto) {
        String paymentId = paymentService.completePayment(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success(paymentId);
    }
}
