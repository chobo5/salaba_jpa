package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.response.RentalHomeDetailResDto;
import salaba.dto.response.ReviewResDto;
import salaba.service.BookMarkService;
import salaba.service.RentalHomeService;
import salaba.util.RestResult;

@Tag(name = "숙소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;

    @Operation(summary = "숙소 상세보기")
    @GetMapping()
    public RestResult<?> getRentalHome(@RequestParam Long rentalHomeId) {
        RentalHomeDetailResDto rentalHomeDetail = rentalHomeService.get(rentalHomeId);
        return RestResult.success(rentalHomeDetail);

    }

    @Operation(summary = "숙소 리뷰 목록")
    @GetMapping("reviews")
    public RestResult<?> getRentalHomeReviews(@RequestParam Long rentalHomeId,
                                              @RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReviewResDto> rentalHomeReviews = rentalHomeService.getRentalHomeReviews(rentalHomeId, pageable);
        return RestResult.success(rentalHomeReviews);
    }

    @Operation(summary = "숙소 리뷰 평균 점수")
    @GetMapping("reviews/avg")
    public RestResult<?> getRentalHomeReviewAvg(@RequestParam Long rentalHomeId) {
        Double rentalHomeReviewAvg = rentalHomeService.getRentalHomeReviewAvg(rentalHomeId);
        return RestResult.success(rentalHomeReviewAvg);
    }
}
