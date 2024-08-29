package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.request.PaymentReqDto;
import salaba.service.PaymentService;
import salaba.util.RestResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("complete")
    public RestResult<?> recordPayment(@RequestBody PaymentReqDto reqDto) {
        return RestResult.success(paymentService.completePayment(reqDto));
    }
}
