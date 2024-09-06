package salaba.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.common.dto.IdResDto;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.dto.response.AlarmResDto;
import salaba.domain.member.dto.response.PointResDto;
import salaba.domain.member.service.MemberService;
import salaba.interceptor.MemberContextHolder;
import salaba.util.RestResult;

import javax.validation.Valid;

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 프로필 수정")
    @PutMapping("modify")
    public RestResult<?> changeProfile(@RequestBody MemberModiReqDto memberModiReqDto) {
        Long memberId = memberService.modifyProfile(MemberContextHolder.getMemberId(), memberModiReqDto);
        return RestResult.success(new IdResDto(memberId));
    }

    @Operation(summary = "회원 비밀번호 변경")
    @PutMapping("changePassword")
    public RestResult<?> changePassword(@RequestBody @Valid ChangePasswordReqDto reqDto) {
        memberService.changePassword(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success();
    }
    
    @Operation(summary = "회원 닉네임 변경")
    @PutMapping("changeNickname")
    public RestResult<?> changeNickname(@RequestBody ChangeNicknameReqDto reqDto) {
        memberService.changeNickname(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success();
    }

    @Operation(summary = "회원 연락처 변경")
    @PutMapping("changeTelNo")
    public RestResult<?> changeTelNo(@RequestBody ChangeTelNoReqDto reqDto) {
        memberService.changeTelNo(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success();
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("resign")
    public RestResult<?> quit(@RequestBody MemberResignReqDto reqDto) {
        memberService.resign(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success();
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
