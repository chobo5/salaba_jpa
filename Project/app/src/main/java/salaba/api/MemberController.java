package salaba.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @GetMapping("wrote/boards")
    public RestResult<?> boardListByMember(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<BoardByMemberResDto> boards = boardService.boardsByMember(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(boards);
    }

    @Operation(summary = "회원이 작성한 댓글 목록")
    @GetMapping("wrote/replies")
    public RestResult<?> replyListByMember(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReplyByMemberResDto> replies = replyService.repliesByMember(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(replies);
    }

    @Operation(summary = "회원의 예약 목록")
    @GetMapping("reservation/list")
    public RestResult<?> reservationList(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReservationToGuestResDto> reservations = reservationService.getWithRentalHomeAndHost(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(reservations);
    }

    @Operation(summary = "회원의 포인트 내역 목록")
    @GetMapping("pointHistory")
    public RestResult<?> getPointHistory(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PointResDto> pointHistory = memberService.getPointHistory(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(pointHistory);
    }

    @Operation(summary = "회원의 최종 적립포인트")
    @GetMapping("totalPoint")
    public RestResult<?> getTotalPoint() {
        int totalPoint = memberService.getTotalPoint(MemberContextHolder.getMemberId());
        return RestResult.success(totalPoint);
    }

    @Operation(summary = "숙소 리뷰 작성")
    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        Long reviewId = memberService.createReview(reviewReqDto);
        return RestResult.success(reviewId);
    }

    @Operation(summary = "회원의 알람 내역")
    @GetMapping("alarms")
    public RestResult<?> getAlarms(@RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AlarmResDto> alarms = memberService.getAlarms(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(alarms);
    }

    @Operation(summary = "숙소 찜하기")
    @PostMapping("mark")
    public RestResult<?> markOnRentalHome(RentalHomeMarkReqDto reqDto) {
        Long bookmarkId = bookMarkService.mark(MemberContextHolder.getMemberId(), reqDto.getRentalHomeId());
        return RestResult.success(bookmarkId);
    }

    @Operation(summary = "숙소 찜하기 취소")
    @DeleteMapping("mark/delete")
    public RestResult<?> deleteMarkOnRentalHome(@RequestParam Long rentalHomeId) {
        bookMarkService.deleteMark(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success();
    }

}
