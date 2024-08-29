package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.MemberJoinReqDto;
import salaba.dto.request.MemberModiReqDto;
import salaba.dto.request.Message;
import salaba.dto.request.ReviewReqDto;
import salaba.exception.AlreadyExistsException;
import salaba.exception.ValidationException;
import salaba.dto.response.IdResDto;
import salaba.service.BoardService;
import salaba.service.MemberService;
import salaba.service.ReplyService;
import salaba.service.ReservationService;
import salaba.util.RestResult;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final ReplyService replyService;

    private final ReservationService reservationService;


    @GetMapping("validateNickname")
    public RestResult<?> validateNickname(@RequestParam String nickname) {
        if (memberService.isExistingNickname(nickname)) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 닉네임 입니다.");
    }

    @GetMapping("validateEmail")
    public RestResult<?> validateEmail(@RequestParam String email) {
        if (memberService.isExistingEmail(email)) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }

    @PostMapping("join")
    public RestResult<?> join(@RequestBody MemberJoinReqDto memberJoinReqDto) {
        return RestResult.success(new IdResDto(memberService.join(memberJoinReqDto)));
    }

    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        return RestResult.success(new IdResDto(memberService.modifyProfile(memberModiReqDto)));
    }

    @GetMapping("wrote/boards/{memberId}")
    public RestResult<?> boardListByMember(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(boardService.boardsByMember(memberId, pageable));
    }

    @GetMapping("wrote/replies/{memberId}")
    public RestResult<?> replyListByMember(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(replyService.repliesByMember(memberId, pageable));
    }

    @GetMapping("reservation/list/{memberId}")
    public RestResult<?> reservationList(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(reservationService.getWithRentalHomeAndHost(memberId, pageable));
    }

    @GetMapping("pointHistory/{memberId}")
    public RestResult<?> getPointHistory(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(memberService.getPointHistory(memberId, pageable));
    }

    @GetMapping("totalPoint/{memberId}")
    public RestResult<?> getTotalPoint(@PathVariable Long memberId) {
        return RestResult.success(memberService.getTotalPoint(memberId));
    }

    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        return RestResult.success(memberService.createReview(reviewReqDto));
    }

    @GetMapping("alarms/{memberId}")
    public RestResult<?> getAlarms(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(memberService.getAlarms(memberId, pageable));
    }

}
