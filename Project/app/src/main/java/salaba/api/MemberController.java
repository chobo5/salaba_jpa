package salaba.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.*;
import salaba.exception.AlreadyExistsException;
import salaba.dto.response.IdResDto;
import salaba.service.BoardService;
import salaba.service.MemberService;
import salaba.service.ReplyService;
import salaba.service.ReservationService;
import salaba.util.RestResult;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final ReplyService replyService;

    private final ReservationService reservationService;


    @ApiOperation(value = "회원 닉네임 사용가능 여부 확인")
    @GetMapping("validateNickname")
    public RestResult<?> validateNickname(@ApiParam(value = "nickname", required = true) @RequestParam String nickname) {
        if (memberService.isExistingNickname(nickname)) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 닉네임 입니다.");
    }

    @ApiOperation(value = "회원 이메일 사용가능 여부 확인")
    @GetMapping("validateEmail")
    public RestResult<?> validateEmail(@ApiParam(value = "email", required = true) @RequestParam String email) {
        if (memberService.isExistingEmail(email)) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("join")
    public RestResult<?> join(@RequestBody MemberJoinReqDto memberJoinReqDto) {
        return RestResult.success(new IdResDto(memberService.join(memberJoinReqDto)));
    }

    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        return RestResult.success(new IdResDto(memberService.modifyProfile(memberModiReqDto)));
    }

    @PutMapping("changePassword")
    public RestResult<?> changePassword(@RequestBody ChangePasswordReqDto reqDto) {
        memberService.changePassword(reqDto.getId(), reqDto.getPassword());
        return RestResult.success();
    }

    @PutMapping("changeNickname")
    public RestResult<?> changeNickname(@RequestBody ChangeNicknameReqDto reqDto) {
        memberService.changeNickname(reqDto.getId(), reqDto.getNickname());
        return RestResult.success();
    }

    @PutMapping("changeTelNo")
    public RestResult<?> changeTelNo(@RequestBody ChangeTelNoReqDto reqDto) {
        memberService.changeTelNo(reqDto.getId(), reqDto.getTelNo());
        return RestResult.success();
    }

    @DeleteMapping("quit")
    public RestResult<?> quit(@RequestBody MemberQuitReqDto reqDto) {
        memberService.quit(reqDto.getEmail(), reqDto.getPassword());
        return RestResult.success();
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
