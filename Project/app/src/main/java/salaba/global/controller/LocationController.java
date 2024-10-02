package salaba.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.global.dto.NationDto;
import salaba.global.service.NationService;
import salaba.util.RestResult;

import java.util.List;

@Tag(name = "국가/지역 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nation")
public class LocationController {

    private final NationService nationService;

    @Operation(summary = "국가 목록")
    @GetMapping("list")
    public RestResult<?> list() {
        List<NationDto> nations = nationService.list();
        return RestResult.success(nations);
    }
}
