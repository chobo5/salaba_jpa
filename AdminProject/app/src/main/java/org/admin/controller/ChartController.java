package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.ChartService;
import org.admin.util.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/chart")
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/boardCount")
    public RestResult<?> boardCountInMonth() {
        return RestResult.success(chartService.getBoardCountInMonth());
    }

    @GetMapping("/joinCount")
    public RestResult<?> joinCountInMonth() {
        return RestResult.success(chartService.getJoinCountInMonth());
    }

    @GetMapping("/gradeCount")
    public RestResult<?> userCountByGrade() {
        return RestResult.success(chartService.getUserCountByGrade());
    }

    @GetMapping("/rentalCount")
    public RestResult<?> rentalCountByRegion() {
        return RestResult.success(chartService.getRentalCountByRegion());
    }

    @GetMapping("/unprocessed")
    public RestResult<?> unprocessedWorks() {
        return RestResult.success(chartService.getUnprocessedWorks());
    }
}
