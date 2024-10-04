package salaba.domain.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.domain.global.dto.NationDto;
import salaba.domain.global.dto.RegionCreateDto;
import salaba.domain.global.dto.RegionDto;
import salaba.domain.global.service.NationService;
import salaba.domain.global.service.RegionService;
import salaba.util.RestResult;

import java.util.List;

@Tag(name = "국가/지역 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class LocationController {

    private final NationService nationService;
    private final RegionService regionService;

    @Operation(summary = "국가 목록")
    @GetMapping("nation/list")
    public RestResult<List<NationDto>> nationList() {
        List<NationDto> nations = nationService.list();
        return RestResult.success(nations);
    }

    @Operation(summary = "지역 목록")
    @GetMapping("region/list")
    public RestResult<List<RegionDto>> regionList(@RequestParam Integer nationId) {
        List<RegionDto> regions = regionService.list(nationId);
        return RestResult.success(regions);
    }

    @Operation(summary = "국가 추가")
    @PostMapping("nation/add")
    public RestResult<NationDto> addNation(@RequestBody NationDto nationDto) {
        NationDto createdNation = nationService.add(nationDto);
        return RestResult.success(createdNation);
    }

    @Operation(summary = "지역 추가")
    @PostMapping("region/add")
    public RestResult<RegionDto> addRegion(@RequestBody RegionCreateDto reqDto) {
        RegionDto createdRegion = regionService.add(reqDto);
        return RestResult.success(createdRegion);
    }


}
