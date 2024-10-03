package salaba.domain.member.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import salaba.domain.auth.constant.RoleName;
import salaba.domain.auth.dto.RefreshTokenDto;
import salaba.domain.auth.dto.request.MemberJoinReqDto;
import salaba.domain.auth.dto.request.MemberLoginReqDto;
import salaba.domain.auth.dto.response.TokenResDto;
import salaba.domain.auth.service.AuthService;
import salaba.domain.auth.service.TokenService;
import salaba.domain.member.entity.Member;
import salaba.domain.auth.entity.MemberRole;
import salaba.domain.auth.entity.Role;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.repository.MemberRoleRepository;
import salaba.domain.member.repository.RoleRepository;
import salaba.domain.member.exception.AlreadyExistsException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService; // 테스트할 서비스 객체

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MemberRoleRepository memberRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Test
    public void 이메일유효성검사_사용가능() {
        // given

        String email = "test@test.com";

        //when
        when(memberRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        //then
        assertDoesNotThrow(() -> authService.isExistingEmail(email));

    }

    @Test
    public void 이메일유효성검사_사용불가() {
        // given
        String email = "test@test.com";

        //when
        Member existingMember = Member.create("test@test.com", "Aabc12432532!@", "test",
                "test", LocalDate.of(1996,10,8));

        when(memberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));

        //then
        assertThrows(AlreadyExistsException.class, () -> authService.isExistingEmail(email));

    }

    @Test
    public void 닉네임유효성검사_사용가능() {
        // given
        String nickname = "testNick";

        //when
        when(memberRepository.findByNickname(nickname))
                .thenReturn(Optional.empty());

        //then
        assertDoesNotThrow(() -> authService.isExistingNickname(nickname));

    }

    @Test
    public void 닉네임유효성검사_사용불가() {
        // given
        String nickname = "testNick";

        //when
        Member existingMember = Member.create("test@test.com", "Aabc12432532!@", "test",
                "testNick", LocalDate.of(1996,10,8));

        when(memberRepository.findByNickname(nickname))
                .thenReturn(Optional.of(existingMember));

        //then
        assertThrows(AlreadyExistsException.class, () -> authService.isExistingNickname(nickname));

    }

    @Test
    public void 회원가입_성공() {
        //given
        MemberJoinReqDto reqDto = new MemberJoinReqDto("testNick", "testName",
                "test@test.com", "Aa123431232!@", LocalDate.of(1996, 10, 8));

        //when
        when(memberRepository.findByNickname(reqDto.getNickname())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(reqDto.getEmail())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(reqDto.getPassword())).thenReturn("encodedPassword");

        Role role = new Role(RoleName.MEMBER.getId(),RoleName.MEMBER);
        when(roleRepository.findByRoleName(RoleName.MEMBER)).thenReturn(Optional.of(role));

        authService.join(reqDto);

        //then
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(memberRoleRepository, times(1)).save(any(MemberRole.class));

    }

    @Test
    public void 회원가입_실패_닉네임중복() {
        //given
        MemberJoinReqDto reqDto = new MemberJoinReqDto("testNick", "testName",
                "test@test.com", "Aa123431232!@", LocalDate.of(1996, 10, 8));

        //when
        when(memberRepository.findByNickname(reqDto.getNickname()))
                .thenReturn(Optional.of(Member.create("", "", "", "testNick",
                        LocalDate.of(2022, 12, 12))));

        //then
        assertThrows(AlreadyExistsException.class, () -> authService.join(reqDto));
        verify(memberRepository, times(1)).findByNickname(reqDto.getNickname());
        verify(memberRepository, times(0)).findByEmail(reqDto.getEmail());
        verify(passwordEncoder, times(0)).encode(reqDto.getPassword());
        verify(memberRepository, times(0)).save(any(Member.class));
        verify(roleRepository, times(0)).findByRoleName(RoleName.MEMBER);
        verify(memberRoleRepository, times(0)).save(any(MemberRole.class));

    }

    @Test
    public void 회원가입_실패_이메일중복() {
        //given
        MemberJoinReqDto reqDto = new MemberJoinReqDto("testNick", "testName",
                "test@test.com", "Aa123431232!@", LocalDate.of(1996, 10, 8));

        //when
        when(memberRepository.findByNickname(reqDto.getNickname())).thenReturn(Optional.empty());

        when(memberRepository.findByEmail(reqDto.getEmail()))
                .thenReturn(Optional.of(Member.create("test@test.com", "", "", "",
                        LocalDate.of(2022, 12, 12))));

        //then
        assertThrows(AlreadyExistsException.class, () -> authService.join(reqDto));
        verify(memberRepository, times(1)).findByNickname(reqDto.getNickname());
        verify(memberRepository, times(1)).findByEmail(reqDto.getEmail());
        verify(passwordEncoder, times(0)).encode(reqDto.getPassword());
        verify(memberRepository, times(0)).save(any(Member.class));
        verify(roleRepository, times(0)).findByRoleName(RoleName.MEMBER);
        verify(memberRoleRepository, times(0)).save(any(MemberRole.class));

    }

    @Test
    public void 로그인_성공() {
        //given
        MemberLoginReqDto reqDto = new MemberLoginReqDto("test@test.com", "Aa12345678!@");

        //when
        Member member = Member.create("test@test.com", "encodedPassword", "", "",
                LocalDate.of(2022, 12, 12));
        when(memberRepository.findByEmail(reqDto.getEmail()))
                .thenReturn(Optional.of(member));

        when(passwordEncoder.matches(reqDto.getPassword(), "encodedPassword")).thenReturn(true);

        when(tokenService.createTokens(member)).thenReturn(new TokenResDto());

        authService.login(reqDto);

        //then
        verify(memberRepository, times(1)).findByEmail(reqDto.getEmail());
        verify(passwordEncoder, times(1)).matches(reqDto.getPassword(), "encodedPassword");
        //lastLoginDate update
        assertThat(member.getLastLoginDate().plusMinutes(1)).isAfter(LocalDateTime.now());
        verify(tokenService, times(1)).createTokens(member);

    }

    @Test
    public void 로그인_실패_없는회원() {
        //given
        MemberLoginReqDto reqDto = new MemberLoginReqDto("test@test.com", "Aa12345678!@");

        //when
        Member member = Member.create("test2@test.com", "encodedPassword", "", "",
                LocalDate.of(2022, 12, 12));
        when(memberRepository.findByEmail(reqDto.getEmail()))
                .thenReturn(Optional.empty());


        //then
        assertThrows(NoSuchElementException.class, () -> authService.login(reqDto));
        verify(memberRepository, times(1)).findByEmail(reqDto.getEmail());
        verify(passwordEncoder, times(0)).matches(reqDto.getPassword(), "encodedPassword");
        verify(tokenService, times(0)).createTokens(member);

    }

    @Test
    public void 로그인_실패_비밀번호불일치() {
        //given
        MemberLoginReqDto reqDto = new MemberLoginReqDto("test@test.com", "Aa12345678!@");

        //when
        Member member = Member.create("test@test.com", "encodedPassword", "", "",
                LocalDate.of(2022, 12, 12));
        when(memberRepository.findByEmail(reqDto.getEmail()))
                .thenReturn(Optional.of(member));

        when(passwordEncoder.matches(reqDto.getPassword(), "encodedPassword")).thenReturn(false);

        //then
        assertThrows(NoSuchElementException.class, () -> authService.login(reqDto));
        verify(memberRepository, times(1)).findByEmail(reqDto.getEmail());
        verify(passwordEncoder, times(1)).matches(reqDto.getPassword(), "encodedPassword");
        verify(tokenService, times(0)).createTokens(member);

    }

    @Test
    public void 로그아웃() {
        //given
        Long memberId = 1L;
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("refreshToken");

        authService.logout(memberId, refreshTokenDto);

        verify(tokenService, times(1)).deleteRefreshToken(memberId, refreshTokenDto.getRefreshToken());
    }





}