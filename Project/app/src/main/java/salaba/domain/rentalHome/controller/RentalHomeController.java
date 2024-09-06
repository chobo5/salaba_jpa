package salaba.domain.rentalHome.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.common.dto.IdResDto;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeMarkReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeModiReqDto;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.dto.response.ReviewResDto;
import salaba.domain.rentalHome.service.BookMarkService;
import salaba.domain.rentalHome.service.RentalHomeService;
import salaba.domain.rentalHome.service.ReviewService;
import salaba.interceptor.MemberContextHolder;
import salaba.util.RestResult;

@Tag(name = "숙소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;
    private final ReviewService reviewService;

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

    @Operation(summary = "숙소 등록")
    @PostMapping("new")
    public RestResult<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        Long rentalHomeId = rentalHomeService.createRentalHome(MemberContextHolder.getMemberId(), rentalHomeCreateReqDto);
        return RestResult.success(new IdResDto(rentalHomeId));
    }

    @Operation(summary = "호스트 소유 숙소 목록")
    @GetMapping("host/list")
    public RestResult<?> getRentalHomeList(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RentalHomeResDto> rentalHomes = rentalHomeService.getRentalHomesByHost(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(rentalHomes);
    }

    @Operation(summary = "호스트 소유 숙소 상세")
    @GetMapping("host/detail")
    public RestResult<?> getRentalHomeDetail(@RequestParam Long rentalHomeId) {
        RentalHomeDetailResDto rentalHomeDetail = rentalHomeService.getRentalHomeByHost(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success(rentalHomeDetail);
    }

    @Operation(summary = "호스트 소유 숙소 수정")
    @PutMapping("modify")
    public RestResult<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return RestResult.success(rentalHomeService.modifyRentalHome(MemberContextHolder.getMemberId(), rentalHomeModiReqDto));
    }

    @Operation(summary = "호스트 소유 숙소 폐쇄(삭제)")
    @DeleteMapping("delete")
    public RestResult<?> deleteRentalHome(@RequestParam Long rentalHomeId) {
        return RestResult.success(rentalHomeService.deleteRentalHome(MemberContextHolder.getMemberId(), rentalHomeId));
    }

    @Operation(summary = "숙소 리뷰 작성")
    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        Long reviewId = reviewService.createReview(reviewReqDto);
        return RestResult.success(reviewId);
    }

    @Operation(summary = "숙소 찜하기")
    @PostMapping("bookmark")
    public RestResult<?> markOnRentalHome(RentalHomeMarkReqDto reqDto) {
        Long bookmarkId = bookMarkService.mark(MemberContextHolder.getMemberId(), reqDto.getRentalHomeId());
        return RestResult.success(bookmarkId);
    }

    @Operation(summary = "숙소 찜하기 취소")
    @DeleteMapping("bookmark/delete")
    public RestResult<?> deleteMarkOnRentalHome(@RequestParam Long rentalHomeId) {
        bookMarkService.deleteMark(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success();
    }
}
