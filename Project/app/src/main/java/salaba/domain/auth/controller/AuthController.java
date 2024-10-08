package salaba.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import salaba.domain.auth.dto.request.MemberJoinReqDto;
import salaba.domain.auth.dto.request.MemberLoginReqDto;
import salaba.domain.global.dto.IdResDto;
import salaba.domain.auth.dto.response.MemberLoginResDto;
import salaba.domain.auth.dto.RefreshTokenDto;
import salaba.domain.auth.service.AuthService;
import salaba.domain.auth.interceptor.MemberContextHolder;
import salaba.util.Regex;
import salaba.util.RestResult;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
@Tag(name = "회원 인증 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원 닉네임 사용가능 여부 확인")
    @GetMapping("validateNickname")
    public RestResult<?> validateNickname(@RequestParam @Pattern(regexp = Regex.NICKNAME, message = Regex.NICKNAME_ERROR) String nickname) {
        authService.isExistingNickname(nickname);
        return RestResult.success();

    }

    @Operation(summary = "회원 이메일 사용가능 여부 확인")
    @GetMapping("validateEmail")
    public RestResult<?> validateEmail(@RequestParam @Email(message = Regex.EMAIL_ERROR) String email) {
        authService.isExistingEmail(email);
        return RestResult.success();
    }

    @Operation(summary = "회원 가입")
    @PostMapping("join")
    public RestResult<?> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {
        Long memberId = authService.join(memberJoinReqDto);
        return RestResult.success(new IdResDto(memberId));
    }


    @Operation(summary = "회원 로그인")
    @PostMapping("/login")
    public RestResult<?> login(@RequestBody @Valid MemberLoginReqDto reqDto) {
        MemberLoginResDto loginResDto = authService.login(reqDto);
        return RestResult.success(loginResDto);
    }

    @Operation(summary = "로그아웃")
    @DeleteMapping("/logout")
    public RestResult<?> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        authService.logout(MemberContextHolder.getMemberId(), refreshTokenDto);
        // token repository에서 refresh Token에 해당하는 값을 삭제한다.
        return RestResult.success();
    }

//    @PostMapping("/refreshToken")
//    public RestResult<?> requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
//        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken());
//        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
//        Long memberNo = Long.valueOf((Integer) claims.get("memberId"));
//
//        Member member = memberService.getMemberBy(memberNo);
//
//        List roles = (List) claims.get("roles");
//        String email = claims.getSubject();
//
//        String accessToken = jwtTokenizer.createAccessToken(memberNo, email, roles);
//
//        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshTokenDto.getRefreshToken())
//                .memberNo(member.getMemberNo())
//                .name(member.getName())
//                .build();
//        return RestResult.success(loginResponse);
//    }


}