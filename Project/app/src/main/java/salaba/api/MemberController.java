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
import salaba.exception.PasswordValidationException;
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
    public RestResult<Message> validateNickname(@RequestParam String nickname) {
        if (memberService.validateNickname(nickname)) {
            return RestResult.success(new Message(HttpStatus.OK.value(), "사용 가능한 닉네임 입니다."));
        }
        return RestResult.status(HttpStatus.CONFLICT).body(new Message(HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임 입니다."));
    }

    @GetMapping("validateEmail")
    public RestResult<Message> validateEmail(@RequestParam String email) {
        if (memberService.validateEmail(email)) {
            return RestResult.success(new Message(HttpStatus.OK.value(), "사용 가능한 이메일 입니다."));
        }
        return RestResult.status(HttpStatus.CONFLICT).body(new Message(HttpStatus.CONFLICT.value(), "이미 사용중인 이메일 입니다."));
    }

    @PostMapping("join")
    public RestResult<?> join(@RequestBody MemberJoinReqDto memberJoinReqDto) {
        try {
            return RestResult
                    .ok(new IdResDto(memberService.join(memberJoinReqDto)));
        } catch (PasswordValidationException | AlreadyExistsException exception) {
            return RestResult
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        }
    }

    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {

        try {
            return RestResult.success(new IdResDto(memberService.modifyProfile(memberModiReqDto)));
        } catch (NoSuchElementException exception) {
            return RestResult.status(HttpStatus.NOT_FOUND).body(new Message(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원입니다."));
        }
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
