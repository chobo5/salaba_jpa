package salaba.api;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.*;
import salaba.dto.response.IdResDto;
import salaba.exception.AlreadyExistsException;
import salaba.security.dto.MemberSignupDto;
import salaba.security.jwt.util.JwtTokenizer;
import salaba.service.MemberService;
import salaba.util.RestResult;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원 닉네임 사용가능 여부 확인")
    @GetMapping("validateNickname")
    public RestResult<?> validateNickname(@RequestBody @Valid ValidateNicknameReqDto reqDto) {
        if (memberService.isExistingNickname(reqDto.getNickname())) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 닉네임 입니다.");
    }

    @ApiOperation(value = "회원 이메일 사용가능 여부 확인")
    @GetMapping("validateEmail")
    public RestResult<?> validateEmail(@RequestBody @Valid ValidateEmailReqDto reqDto) {
        if (memberService.isExistingEmail(reqDto.getEmail())) {
            return RestResult.success();
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("join")
    public RestResult<?> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {
        return RestResult.success(new IdResDto(memberService.join(memberJoinReqDto)));
    }


    @ApiOperation(value = "회원 비밀번호 변경")
    @PutMapping("changePassword")
    public RestResult<?> changePassword(@RequestBody ChangePasswordReqDto reqDto) {
        memberService.changePassword(reqDto.getMemberId(), reqDto.getPassword());
        return RestResult.success();
    }

//    @PostMapping("/login")
//    public RestResult<?> login(@RequestBody @Valid MemberLoginReqDto reqDto) {
//
//        // email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
//        Member member = memberService.getByEmail(loginDto.getEmail());
//
//
//        return RestResult.success(loginResponse);
//    }

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