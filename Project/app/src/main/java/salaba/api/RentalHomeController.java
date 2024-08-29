package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.BookMarkService;
import salaba.service.RentalHomeService;
import salaba.util.RestResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;
    @PostMapping("new")
    public RestResult<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        return RestResult.success(new IdResDto(rentalHomeService.createRentalHome(rentalHomeCreateReqDto)));
    }

    @GetMapping("{rentalHomeId}")
    public RestResult<?> getRentalHome(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.get(rentalHomeId));

    }

    @PostMapping("mark/{memberId}/{rentalHomeId}")
    public RestResult<?> markOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        return RestResult.success(bookMarkService.mark(memberId, rentalHomeId));
    }

    @DeleteMapping("mark/delete/{memberId}/{rentalHomeId}")
    public RestResult<?> deleteMarkOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        try {
            Long result = bookMarkService.deleteMark(memberId, rentalHomeId);
            return RestResult.success(result);
        } catch (Exception e) {
            return RestResult.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("reviews/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviews(@PathVariable Long rentalHomeId, Pageable pageable) {
        return RestResult.success(rentalHomeService.getRentalHomeReviews(rentalHomeId, pageable));
    }

    @GetMapping("reviews/avg/{rentalHomeId}")
    public RestResult<?> getRentalHomeReviewAvg(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.getRentalHomeReviewAvg(rentalHomeId));
    }
}
