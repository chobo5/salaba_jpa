package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.request.PaymentReqDto;
import salaba.service.PaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("complete")
    public ResponseEntity<?> recordPayment(@RequestBody PaymentReqDto reqDto) {
        return ResponseEntity.ok(paymentService.completePayment(reqDto));
    }
}
