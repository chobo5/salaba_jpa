package salaba.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.*;
import salaba.dto.response.*;
import salaba.exception.AlreadyExistsException;
import salaba.service.*;
import salaba.util.MemberContextHolder;
import salaba.util.RestResult;

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final ReplyService replyService;

    private final ReservationService reservationService;

    private final BookMarkService bookMarkService;



    @Operation(summary = "회원 프로필 수정")
    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        Long memberId = memberService.modifyProfile(MemberContextHolder.getMemberId(), memberModiReqDto);
        return RestResult.success(new IdResDto(memberId));
    }

    @Operation(summary = "회원이 작성한 게시물 목록")
    @GetMapping("wrote/boards/{memberId}")
    public RestResult<?> boardListByMember(@PathVariable Long memberId, Pageable pageable) {
        Page<BoardByMemberResDto> boards = boardService.boardsByMember(memberId, pageable);
        return RestResult.success(boards);
    }

    @Operation(summary = "회원이 작성한 댓글 목록")
    @GetMapping("wrote/replies/{memberId}")
    public RestResult<?> replyListByMember(@PathVariable Long memberId, Pageable pageable) {
        Page<ReplyByMemberResDto> replies = replyService.repliesByMember(memberId, pageable);
        return RestResult.success(replies);
    }

    @Operation(summary = "회원의 예약 목록")
    @GetMapping("reservation/list/{memberId}")
    public RestResult<?> reservationList(@PathVariable Long memberId, Pageable pageable) {
        Page<ReservationToGuestResDto> reservations = reservationService.getWithRentalHomeAndHost(memberId, pageable);
        return RestResult.success(reservations);
    }

    @Operation(summary = "회원의 포인트 내역 목록")
    @GetMapping("pointHistory/{memberId}")
    public RestResult<?> getPointHistory(@PathVariable Long memberId, Pageable pageable) {
        Page<PointResDto> pointHistory = memberService.getPointHistory(memberId, pageable);
        return RestResult.success(pointHistory);
    }

    @Operation(summary = "회원의 최종 적립포인트")
    @GetMapping("totalPoint/{memberId}")
    public RestResult<?> getTotalPoint(@PathVariable Long memberId) {
        int totalPoint = memberService.getTotalPoint(memberId);
        return RestResult.success(totalPoint);
    }

    @Operation(summary = "숙소 리뷰 작성")
    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        Long reviewId = memberService.createReview(reviewReqDto);
        return RestResult.success(reviewId);
    }

    @Operation(summary = "회원의 알람 내역")
    @GetMapping("alarms/{memberId}")
    public RestResult<?> getAlarms(@PathVariable Long memberId, Pageable pageable) {
        Page<AlarmResDto> alarms = memberService.getAlarms(memberId, pageable);
        return RestResult.success(alarms);
    }

    @Operation(summary = "숙소 찜하기")
    @PostMapping("mark/{memberId}/{rentalHomeId}")
    public RestResult<?> markOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        Long bookmarkId = bookMarkService.mark(memberId, rentalHomeId);
        return RestResult.success(bookmarkId);
    }

    @Operation(summary = "숙소 찜하기 취소")
    @DeleteMapping("mark/delete/{memberId}/{rentalHomeId}")
    public RestResult<?> deleteMarkOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        bookMarkService.deleteMark(memberId, rentalHomeId);
        return RestResult.success();
    }

}
