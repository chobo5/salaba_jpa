package salaba.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.BookMarkService;
import salaba.service.RentalHomeService;
import salaba.util.RestResult;

@Api(tags = "숙소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;

    @ApiOperation("숙소 상세보기")
    @GetMapping("{rentalHomeId}")
    public RestResult<?> getRentalHome(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.get(rentalHomeId));

    }

    @ApiOperation("숙소 리뷰 목록")
    @GetMapping("reviews/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviews(@PathVariable Long rentalHomeId, Pageable pageable) {
        return RestResult.success(rentalHomeService.getRentalHomeReviews(rentalHomeId, pageable));
    }

    @ApiOperation("숙소 리뷰 평균 점수")
    @GetMapping("reviews/avg/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviewAvg(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.getRentalHomeReviewAvg(rentalHomeId));
    }
}
