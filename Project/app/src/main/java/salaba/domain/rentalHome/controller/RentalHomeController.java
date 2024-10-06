package salaba.domain.rentalHome.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.global.dto.IdResDto;
import salaba.domain.rentalHome.dto.request.*;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.dto.response.ReviewResDto;
import salaba.domain.member.service.BookMarkService;
import salaba.domain.rentalHome.service.RentalHomeService;
import salaba.domain.reservation.service.ReviewService;
import salaba.domain.auth.interceptor.MemberContextHolder;
import salaba.util.RestResult;


@Tag(name = "숙소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
@Slf4j
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    private final BookMarkService bookMarkService;
    private final ReviewService reviewService;

    @Operation(summary = "숙소 상세보기")
    @GetMapping("detail")
    public RestResult<?> getRentalHome(@RequestParam Long rentalHomeId) {
        RentalHomeDetailResDto rentalHomeDetail = rentalHomeService.view(rentalHomeId);
        return RestResult.success(rentalHomeDetail);

    }

    @Operation(summary = "숙소 리뷰 목록")
    @GetMapping("reviews")
    public RestResult<?> getRentalHomeReviews(@RequestParam Long rentalHomeId,
                                              @RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReviewResDto> rentalHomeReviews = reviewService.getRentalHomeReviews(rentalHomeId, pageable);
        return RestResult.success(rentalHomeReviews);
    }

    @Operation(summary = "숙소 리뷰 평균 점수")
    @GetMapping("reviews/avg")
    public RestResult<?> getRentalHomeReviewAvg(@RequestParam Long rentalHomeId) {
        Double rentalHomeReviewAvg = reviewService.getRentalHomeReviewAvg(rentalHomeId);
        return RestResult.success(rentalHomeReviewAvg);
    }

    @Operation(summary = "숙소 등록")
    @PostMapping("new")
    public RestResult<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        Long rentalHomeId = rentalHomeService.create(MemberContextHolder.getMemberId(), rentalHomeCreateReqDto);
        return RestResult.success(new IdResDto(rentalHomeId));
    }

    @Operation(summary = "호스트 소유 숙소 목록")
    @GetMapping("host/list")
    public RestResult<?> getRentalHomeList(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RentalHomeResDto> rentalHomes = rentalHomeService.getRentalHomesOwnedByHost(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(rentalHomes);
    }

    @Operation(summary = "호스트 소유 숙소 상세")
    @GetMapping("host/detail")
    public RestResult<?> getRentalHomeDetail(@RequestParam Long rentalHomeId) {
        RentalHomeDetailResDto rentalHomeDetail = rentalHomeService.getRentalHomeOwnedByHost(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success(rentalHomeDetail);
    }

    @Operation(summary = "호스트 소유 숙소 수정")
    @PutMapping("modify")
    public RestResult<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return RestResult.success(rentalHomeService.modify(MemberContextHolder.getMemberId(), rentalHomeModiReqDto));
    }

    @Operation(summary = "호스트 소유 숙소 폐쇄(삭제)")
    @DeleteMapping("delete")
    public RestResult<?> deleteRentalHome(@RequestParam Long rentalHomeId) {
        return RestResult.success(rentalHomeService.delete(MemberContextHolder.getMemberId(), rentalHomeId));
    }

    @Operation(summary = "숙소 리뷰 작성")
    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        Long reviewId = reviewService.createReview(reviewReqDto, MemberContextHolder.getMemberId());
        return RestResult.success(reviewId);
    }

    @Operation(summary = "숙소 리뷰 수정")
    @PutMapping("reservation/review/modify")
    public RestResult<?> modifyReview(@RequestBody ReviewModiReqDto reqDto) {
        reviewService.modifyReview(reqDto, MemberContextHolder.getMemberId());
        return RestResult.success();
    }

    @Operation(summary = "숙소 리뷰 삭제")
    @DeleteMapping("reservation/review/delete")
    public RestResult<?> deleteReview(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId, MemberContextHolder.getMemberId());
        return RestResult.success();
    }

    @Operation(summary = "숙소 찜하기")
    @PostMapping("bookmark")
    public RestResult<?> markOnRentalHome(RentalHomeMarkReqDto reqDto) {
        Long bookmarkId = bookMarkService.create(MemberContextHolder.getMemberId(), reqDto.getRentalHomeId());
        return RestResult.success(bookmarkId);
    }

    @Operation(summary = "숙소 찜하기 취소")
    @DeleteMapping("bookmark/delete")
    public RestResult<?> deleteMarkOnRentalHome(@RequestParam Long rentalHomeId) {
        bookMarkService.delete(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success();
    }

    @Operation(summary = "숙소 찾기(리뷰순)")
    @GetMapping("search/orderByReview")
    public RestResult<?> searchRentalHomeOrderByReview(@RequestParam(required = false) String region,
                                          @RequestParam(required = false) String theme,
                                          @RequestParam(required = false) Long minPrice,
                                          @RequestParam(required = false) Long maxPrice,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RentalHomeResDto> rentalHomeResDtos = rentalHomeService
                .searchRentalHomesOrderByReview(region, theme, minPrice, maxPrice, pageable);
        return RestResult.success(rentalHomeResDtos);
    }

    @Operation(summary = "숙소 찾기(판매순)")
    @GetMapping("search/orderBySalesCount")
    public RestResult<?> searchRentalHomeOrderBySalesCount(@RequestParam(required = false) String region,
                                          @RequestParam(required = false) String theme,
                                          @RequestParam(required = false) Long minPrice,
                                          @RequestParam(required = false) Long maxPrice,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RentalHomeResDto> rentalHomeResDtos = rentalHomeService
                .searchRentalHomesOrderBySalesCount(region, theme, minPrice, maxPrice, pageable);
        return RestResult.success(rentalHomeResDtos);
    }


}
