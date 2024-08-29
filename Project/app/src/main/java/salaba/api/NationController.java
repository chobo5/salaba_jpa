package salaba.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.service.NationService;
import salaba.util.RestResult;

@Api(tags = "국가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nation")
public class NationController {

    private final NationService nationService;

    @GetMapping("list")
    public RestResult<?> list() {
        return RestResult.success(nationService.list());
    }
}
