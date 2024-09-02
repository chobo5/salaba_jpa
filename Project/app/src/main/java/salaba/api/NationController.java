package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.service.NationService;
import salaba.util.RestResult;

@Tag(name = "국가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nation")
public class NationController {

    private final NationService nationService;

    @Operation(summary = "국가 목록")
    @GetMapping("list")
    public RestResult<?> list() {
        return RestResult.success(nationService.list());
    }
}
