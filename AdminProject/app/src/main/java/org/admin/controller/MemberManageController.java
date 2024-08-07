package org.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.admin.domain.Member;
import org.admin.service.MemberService;
import org.admin.util.MemberType;
import org.admin.util.RestResult;
import org.admin.util.SearchType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping("/list/{memberType}")
    public RestResult<?> memberList(@PathVariable int memberType) {
        if (memberType == MemberType.MEMBER.getValue()) {
            return RestResult.success(memberService.getAll());
        }

        if (memberType == MemberType.HOST.getValue()) {
            return RestResult.success(memberService.getAllHosts());
        }

        return RestResult.error("유효하지 않은 memberType 입니다.");
    }


    @GetMapping("/view/{memberNo}/{memberType}")
    public RestResult<?> memberView(@PathVariable long memberNo,
                                    @PathVariable int memberType) {

        if (memberType == MemberType.MEMBER.getValue()) {
            return RestResult.success(memberService.getMemberBy(memberNo));
        }
        if (memberType == MemberType.HOST.getValue()) {
            return RestResult.success(memberService.getHostBy(memberNo));
        }

        return RestResult.error("유효하지 않은 memberType 입니다.");

    }

    @GetMapping("/search/{keyword}/{filter}/{memberType}")
    public RestResult<?> searchMember(@PathVariable String keyword,
                                      @PathVariable String filter,
                                      @PathVariable int memberType) {
        //일반 회원 목룍
        if (memberType == MemberType.MEMBER.getValue()) {
            if (filter.equals(SearchType.NAME.getValue())) {
                //이름으로 검색
                return RestResult.success(memberService.getMemberByName(keyword));
            } else {
                return RestResult.success(memberService.getMemberByEmail(keyword));
            }

        }

        // 호스트 목록
        if(memberType == MemberType.HOST.getValue()) {
            if (filter.equals(SearchType.NAME.getValue())) {
                return RestResult.success(memberService.getHostByName(keyword));
            } else {
                // 이메일로 검색
                return RestResult.success(memberService.getHostByEmail(keyword));
            }
        }

        return RestResult.error("유효하지 않은 memberType 입니다.");
    }

    @PutMapping("/update")
    public RestResult<?> updateGrade(@RequestBody Member member) {
        return RestResult.success(memberService.updateGrade(member.getGradeNo(), member.getMemberNo()));
    }
}
