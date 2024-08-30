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
import salaba.service.*;
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

    private final BookMarkService bookMarkService;


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

    @ApiOperation(value = "회원 프로필 수정")
    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        return RestResult.success(new IdResDto(memberService.modifyProfile(memberModiReqDto)));
    }

    @ApiOperation(value = "회원 비밀번호 변경")
    @PutMapping("changePassword")
    public RestResult<?> changePassword(@RequestBody ChangePasswordReqDto reqDto) {
        memberService.changePassword(reqDto.getMemberId(), reqDto.getPassword());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 닉네임 변경")
    @PutMapping("changeNickname")
    public RestResult<?> changeNickname(@RequestBody ChangeNicknameReqDto reqDto) {
        memberService.changeNickname(reqDto.getMemberId(), reqDto.getNickname());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 연락처 변경")
    @PutMapping("changeTelNo")
    public RestResult<?> changeTelNo(@RequestBody ChangeTelNoReqDto reqDto) {
        memberService.changeTelNo(reqDto.getMemberId(), reqDto.getTelNo());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("resign")
    public RestResult<?> quit(@RequestBody MemberResignReqDto reqDto) {
        memberService.resign(reqDto.getEmail(), reqDto.getPassword());
        return RestResult.success();
    }

    @ApiOperation(value = "회원이 작성한 게시물 목록")
    @GetMapping("wrote/boards/{memberId}")
    public RestResult<?> boardListByMember(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(boardService.boardsByMember(memberId, pageable));
    }

    @ApiOperation(value = "회원이 작성한 댓글 목록")
    @GetMapping("wrote/replies/{memberId}")
    public RestResult<?> replyListByMember(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(replyService.repliesByMember(memberId, pageable));
    }

    @ApiOperation(value = "회원의 예약 목록")
    @GetMapping("reservation/list/{memberId}")
    public RestResult<?> reservationList(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(reservationService.getWithRentalHomeAndHost(memberId, pageable));
    }

    @ApiOperation(value = "회원의 포인트 내역 목록")
    @GetMapping("pointHistory/{memberId}")
    public RestResult<?> getPointHistory(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(memberService.getPointHistory(memberId, pageable));
    }

    @ApiOperation(value = "회원의 최종 적립포인트")
    @GetMapping("totalPoint/{memberId}")
    public RestResult<?> getTotalPoint(@PathVariable Long memberId) {
        return RestResult.success(memberService.getTotalPoint(memberId));
    }

    @ApiOperation(value = "숙소 리뷰 작성")
    @PostMapping("reservation/review")
    public RestResult<?> createReview(@RequestBody ReviewReqDto reviewReqDto) {
        return RestResult.success(memberService.createReview(reviewReqDto));
    }

    @ApiOperation(value = "회원의 알람 내역")
    @GetMapping("alarms/{memberId}")
    public RestResult<?> getAlarms(@PathVariable Long memberId, Pageable pageable) {
        return RestResult.success(memberService.getAlarms(memberId, pageable));
    }

    @ApiOperation("숙소 찜하기")
    @PostMapping("mark/{memberId}/{rentalHomeId}")
    public RestResult<?> markOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        return RestResult.success(bookMarkService.mark(memberId, rentalHomeId));
    }

    @ApiOperation("숙소 찜하기 취소")
    @DeleteMapping("mark/delete/{memberId}/{rentalHomeId}")
    public RestResult<?> deleteMarkOnRentalHome(@PathVariable Long memberId, @PathVariable Long rentalHomeId) {
        bookMarkService.deleteMark(memberId, rentalHomeId);
        return RestResult.success();
    }

}
