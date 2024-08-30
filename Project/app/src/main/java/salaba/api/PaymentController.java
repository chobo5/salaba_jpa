package salaba.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.request.PaymentReqDto;
import salaba.service.PaymentService;
import salaba.util.RestResult;
@Api(tags = "결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    private final PaymentService paymentService;

    @ApiOperation("결제 완료")
    @PostMapping("complete")
    public RestResult<?> recordPayment(@RequestBody PaymentReqDto reqDto) {
        return RestResult.success(paymentService.completePayment(reqDto));
    }
}
