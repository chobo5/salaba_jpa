package salaba.warmup;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import salaba.util.RestResult;

import javax.validation.Valid;

@RestController
@Hidden
public class WarmUpController {
    @PostMapping("warm-up")
    public RestResult<?> warmup(@RequestBody @Valid WarmUpRequest warmUpRequest) {
        return RestResult.success(warmUpRequest.toString());
    }
}
