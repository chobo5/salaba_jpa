package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.entity.rental.RentalHome;
import salaba.dto.response.IdResDto;
import salaba.service.BookMarkService;
import salaba.service.RentalHomeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;
    @PostMapping("new")
    public ResponseEntity<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        return ResponseEntity.ok(new IdResDto(rentalHomeService.createRentalHome(rentalHomeCreateReqDto)));
    }

    @GetMapping("{rentalHomeId}")
    public ResponseEntity<?> getRentalHome(@PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(rentalHomeService.get(rentalHomeId));

    }

    @PostMapping("mark/{memberId}/{rentalHomeId}")
    public ResponseEntity<?> markOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(bookMarkService.mark(memberId, rentalHomeId));
    }

    @DeleteMapping("mark/delete/{memberId}/{rentalHomeId}")
    public ResponseEntity<?> deleteMarkOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        try {
            Long result = bookMarkService.deleteMark(memberId, rentalHomeId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("reviews/{rentalHomeId}")
    public ResponseEntity<?> getRentalHomeReviews(@PathVariable Long rentalHomeId, Pageable pageable) {
        return ResponseEntity.ok(rentalHomeService.getRentalHomeReviews(rentalHomeId, pageable));
    }

    @GetMapping("reviews/avg/{rentalHomeId}")
    public ResponseEntity<?> getRentalHomeReviewAvg(@PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(rentalHomeService.getRentalHomeReviewAvg(rentalHomeId));
    }
}
