package salaba.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.*;
import salaba.dto.response.IdResDto;
import salaba.exception.AlreadyExistsException;
import salaba.service.AuthService;
import salaba.util.RestResult;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
@Api(tags = "회원 인증 API")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "회원 닉네임 사용가능 여부 확인")
    @GetMapping("validateNickname")
    public RestResult<?> validateNickname(@ModelAttribute @Valid ValidateNicknameReqDto reqDto) {
        if (authService.isExistingNickname(reqDto.getNickname())) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 닉네임 입니다.");
    }

    @ApiOperation(value = "회원 이메일 사용가능 여부 확인")
    @GetMapping("validateEmail")
    public RestResult<?> validateEmail(@RequestParam String email) {
        ValidateEmailReqDto reqDto = new ValidateEmailReqDto();
        reqDto.setEmail(email);
        if (authService.isExistingEmail(reqDto.getEmail())) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("join")
    public RestResult<?> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {
        return RestResult.success(new IdResDto(authService.join(memberJoinReqDto)));
    }


    @ApiOperation(value = "회원 비밀번호 변경")
    @PutMapping("changePassword")
    public RestResult<?> changePassword(@RequestBody @Valid ChangePasswordReqDto reqDto) {
        authService.changePassword(reqDto.getMemberId(), reqDto.getPassword());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 로그인")
    @PostMapping("/login")
    public RestResult<?> login(@RequestBody @Valid MemberLoginReqDto reqDto) {
        return RestResult.success(authService.login(reqDto));
    }

    @ApiOperation(value = "회원 닉네임 변경")
    @PutMapping("changeNickname")
    public RestResult<?> changeNickname(@RequestBody ChangeNicknameReqDto reqDto) {
        authService.changeNickname(reqDto.getMemberId(), reqDto.getNickname());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 연락처 변경")
    @PutMapping("changeTelNo")
    public RestResult<?> changeTelNo(@RequestBody ChangeTelNoReqDto reqDto) {
        authService.changeTelNo(reqDto.getMemberId(), reqDto.getTelNo());
        return RestResult.success();
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("resign")
    public RestResult<?> quit(@RequestBody MemberResignReqDto reqDto) {
        authService.resign(reqDto);
        return RestResult.success();
    }

//    @DeleteMapping("/logout")
//    public RestResult<?> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
//        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
//        // token repository에서 refresh Token에 해당하는 값을 삭제한다.
//        return RestResult.success();
//    }

//    @PostMapping("/refreshToken")
//    public RestResult<?> requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
//        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken());
//        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
//        Long memberNo = Long.valueOf((Integer) claims.get("userId"));
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