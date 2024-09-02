package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.service.BookMarkService;
import salaba.service.RentalHomeService;
import salaba.util.RestResult;

@Tag(name = "숙소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;

    @Operation(summary = "숙소 상세보기")
    @GetMapping("{rentalHomeId}")
    public RestResult<?> getRentalHome(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.get(rentalHomeId));

    }

    @Operation(summary = "숙소 리뷰 목록")
    @GetMapping("reviews/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviews(@PathVariable Long rentalHomeId, Pageable pageable) {
        return RestResult.success(rentalHomeService.getRentalHomeReviews(rentalHomeId, pageable));
    }

    @Operation(summary = "숙소 리뷰 평균 점수")
    @GetMapping("reviews/avg/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviewAvg(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.getRentalHomeReviewAvg(rentalHomeId));
    }
}
