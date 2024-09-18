package salaba.domain.member.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.repository.NationRepository;
import salaba.domain.member.constants.Gender;
import salaba.domain.member.dto.request.ChangeNicknameReqDto;
import salaba.domain.member.dto.request.ChangePasswordReqDto;
import salaba.domain.member.dto.request.MemberJoinReqDto;
import salaba.domain.member.dto.request.MemberModiReqDto;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.AuthService;
import salaba.exception.AlreadyExistsException;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;
    
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private NationRepository nationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AlarmService alarmService;

    @Mock
    private PointService pointService;

    @Mock
    private AuthService authService;


    @Test
    public void 프로필정보수정() {
        //given
        Long memberId = 1L;
        MemberModiReqDto reqDto = new MemberModiReqDto("modiName", 1,
                Gender.FEMALE, "teststreet", 11111);

        //when
        Member findMember = Member.createMember("test@test.com", "encodedPassword",
                "name","nickname", LocalDate.of(2022, 12, 12));
        when(memberRepository.findById(memberId))
                .thenReturn(Optional.of(findMember));

        when(nationRepository.findById(reqDto.getNationId()))
                .thenReturn(Optional.of(new Nation(reqDto.getNationId(), "testNation")));

        memberService.modifyProfile(memberId, reqDto);

        //then
        assertThat(findMember.getName()).isEqualTo(reqDto.getName());
        assertThat(findMember.getNation().getId()).isEqualTo(reqDto.getNationId());
        assertThat(findMember.getGender()).isEqualTo(reqDto.getGender());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(reqDto.getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(reqDto.getZipcode());
        verify(memberRepository, times(1)).findById(memberId);
        verify(nationRepository, times(1)).findById(reqDto.getNationId());
    }

    @Test
    public void 프로필정보수정_null포함() {
        //given
        Long memberId = 1L;
        MemberModiReqDto reqDto = new MemberModiReqDto("modiName", 1,
                null, null, null);

        //when
        Member findMember = Member.createMember("test@test.com", "encodedPassword",
                "name","nickname", LocalDate.of(2022, 12, 12));

        findMember.changeProfile("name", Gender.FEMALE, new Nation(2, "korea"), new Address("teststreet", 1111));

        when(memberRepository.findById(memberId))
                .thenReturn(Optional.of(findMember));

        when(nationRepository.findById(reqDto.getNationId()))
                .thenReturn(Optional.of(new Nation(reqDto.getNationId(), "testNation")));

        memberService.modifyProfile(memberId, reqDto);

        //then
        assertThat(findMember.getName()).isEqualTo(reqDto.getName());
        assertThat(findMember.getNation().getId()).isEqualTo(reqDto.getNationId());
        assertThat(findMember.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(findMember.getAddress().getStreet()).isEqualTo("teststreet");
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(1111);
        verify(memberRepository, times(1)).findById(memberId);
        verify(nationRepository, times(1)).findById(reqDto.getNationId());
    }

    @Test
    public void 비밀번호변경_성공() {
        //given
        Long memberId = 1L;
        ChangePasswordReqDto reqDto = new ChangePasswordReqDto("기존 비밀번호", "새로운 비밀번호");

        //when
        Member findMember = Member.createMember("test@test.com", "인코딩된 기존 비밀번호",
                "name","nickname", LocalDate.of(2022, 12, 12));

        when(memberRepository.findById(memberId))
                .thenReturn(Optional.of(findMember));

        when(passwordEncoder.matches(reqDto.getPassword(), "인코딩된 기존 비밀번호")).thenReturn(true);

        when(passwordEncoder.encode(reqDto.getNewPassword())).thenReturn("인코딩된 새로운 비밀번호");

        memberService.changePassword(memberId, reqDto);

        //then
        assertThat(findMember.getPassword()).isEqualTo("인코딩된 새로운 비밀번호");
        verify(memberRepository, times(1)).findById(memberId);
        verify(passwordEncoder, times(1)).matches(reqDto.getPassword(), "인코딩된 기존 비밀번호");
        verify(passwordEncoder, times(1)).encode(reqDto.getNewPassword());
    }

    @Test
    public void 비밀번호변경실패_없는회원() {
        //given
        Long memberId = 1L;
        ChangePasswordReqDto reqDto = new ChangePasswordReqDto("기존 비밀번호", "새로운 비밀번호");

        //when
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> memberService.changePassword(memberId, reqDto));
        verify(memberRepository, times(1)).findById(memberId);
        verify(passwordEncoder, times(0)).matches(reqDto.getPassword(), "인코딩된 기존 비밀번호");
        verify(passwordEncoder, times(0)).encode(reqDto.getNewPassword());
    }

    @Test
    public void 비밀번호변경실패_비밀번호불일치() {
        //given
        Long memberId = 1L;
        ChangePasswordReqDto reqDto = new ChangePasswordReqDto("기존 비밀번호", "새로운 비밀번호");

        //when
        Member findMember = Member.createMember("test@test.com", "인코딩된 기존 비밀번호",
                "name","nickname", LocalDate.of(2022, 12, 12));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));

        when(passwordEncoder.matches(reqDto.getPassword(), "인코딩된 기존 비밀번호")).thenReturn(false);

        //then
        assertThrows(ValidationException.class, () -> memberService.changePassword(memberId, reqDto));
        verify(memberRepository, times(1)).findById(memberId);
        verify(passwordEncoder, times(1)).matches(reqDto.getPassword(), "인코딩된 기존 비밀번호");
        verify(passwordEncoder, times(0)).encode(reqDto.getNewPassword());
    }

    @Test
    public void 닉네임변경_성공() {
        //given
        Long memberId = 1L;
        ChangeNicknameReqDto reqDto = new ChangeNicknameReqDto("새로운 닉네임");

        //when
        Member findMember = Member.createMember("test@test.com", "비밀번호",
                "닉네임","nickname", LocalDate.of(2022, 12, 12));

        doNothing().when(authService).isExistingNickname(reqDto.getNickname());
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));

        memberService.changeNickname(memberId, reqDto);

        //then
        verify(authService, times(1)).isExistingNickname(reqDto.getNickname());
        verify(memberRepository, times(1)).findById(memberId);
        assertThat(findMember.getNickname()).isEqualTo(reqDto.getNickname());
    }

    @Test
    public void 닉네임변경실패_없는회원() {
        //given
        Long memberId = 1L;
        ChangeNicknameReqDto reqDto = new ChangeNicknameReqDto("새로운 닉네임");

        //when
        doNothing().when(authService).isExistingNickname(reqDto.getNickname());
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> memberService.changeNickname(memberId, reqDto));
        verify(authService, times(1)).isExistingNickname(reqDto.getNickname());
        verify(memberRepository, times(1)).findById(memberId);
    }


    @Test
    public void 전화번호변경_성공() {

    }

    @Test
    public void 전화번호변경실패_없는회원() {

    }




}