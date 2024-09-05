package salaba.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.board.dto.response.BoardByMemberResDto;
import salaba.domain.board.service.BoardService;
import salaba.domain.common.dto.IdResDto;
import salaba.domain.member.dto.request.MemberModiReqDto;
import salaba.domain.member.dto.response.AlarmResDto;
import salaba.domain.member.dto.response.PointResDto;
import salaba.domain.member.service.MemberService;
import salaba.domain.rentalHome.dto.request.RentalHomeMarkReqDto;
import salaba.domain.rentalHome.dto.request.ReviewReqDto;
import salaba.domain.rentalHome.service.BookMarkService;
import salaba.domain.reply.dto.response.ReplyByMemberResDto;
import salaba.domain.reply.service.ReplyService;
import salaba.domain.reservation.dto.response.ReservationToGuestResDto;
import salaba.domain.reservation.service.ReservationService;
import salaba.security.jwt.util.JwtTokenizer;
import salaba.interceptor.MemberContextHolder;
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
    private final JwtTokenizer jwtTokenizer;


    @Operation(summary = "회원 프로필 수정")
    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        Long memberId = memberService.modifyProfile(MemberContextHolder.getMemberId(), memberModiReqDto);
        return RestResult.success(new IdResDto(memberId));
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

    @Operation(summary = "회원의 알람 내역")
    @GetMapping("alarms")
    public RestResult<?> getAlarms(@RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AlarmResDto> alarms = memberService.getAlarms(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(alarms);
    }

}
