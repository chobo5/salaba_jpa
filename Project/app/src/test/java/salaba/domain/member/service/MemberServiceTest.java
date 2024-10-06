package salaba.domain.member.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import salaba.domain.auth.service.AuthService;
import salaba.domain.auth.service.TokenService;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.repository.NationRepository;
import salaba.domain.member.constants.Gender;
import salaba.domain.auth.constant.MemberStatus;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.domain.member.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Mock
    private TokenService tokenService;


    @Test
    public void 프로필정보수정() {
        //given
        Long memberId = 1L;
        MemberModiReqDto reqDto = new MemberModiReqDto("modiName", 1,
                Gender.FEMALE, "teststreet", 11111);

        //when
        Member findMember = Member.create("test@test.com", "encodedPassword",
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
        Member findMember = Member.create("test@test.com", "encodedPassword",
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
        Member findMember = Member.create("test@test.com", "인코딩된 기존 비밀번호",
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
        assertThrows(EntityNotFoundException.class, () -> memberService.changePassword(memberId, reqDto));
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
        Member findMember = Member.create("test@test.com", "인코딩된 기존 비밀번호",
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
        Member findMember = Member.create("test@test.com", "비밀번호",
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
        assertThrows(EntityNotFoundException.class, () -> memberService.changeNickname(memberId, reqDto));
        verify(authService, times(1)).isExistingNickname(reqDto.getNickname());
        verify(memberRepository, times(1)).findById(memberId);
    }


    @Test
    public void 전화번호변경_성공() {
        //given
        Long memberId = 1L;
        ChangeTelNoReqDto reqDto = new ChangeTelNoReqDto("01011111111");

        //when
        Member findMember = Member.create("test@test.com", "비밀번호",
                "닉네임","nickname", LocalDate.of(2022, 12, 12));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));

        memberService.changeTelNo(memberId, reqDto);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        assertThat(findMember.getTelNo()).isEqualTo(reqDto.getTelNo());
    }

    @Test
    public void 전화번호변경실패_없는회원() {
        //given
        Long memberId = 1L;
        ChangeTelNoReqDto reqDto = new ChangeTelNoReqDto("01011111111");

        //when
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> memberService.changeTelNo(memberId, reqDto));

        //then
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    public void 회원탈퇴() {
        //given
        Long memberId = 1L;
        MemberResignReqDto reqDto = new MemberResignReqDto("비밀번호", "리프레쉬토큰");

        //when
        Member findMember = Member.create("test@test.com", "인코딩된 비밀번호",
                "닉네임","nickname", LocalDate.of(2022, 12, 12));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));
        when(passwordEncoder.matches(reqDto.getPassword(), findMember.getPassword())).thenReturn(true);

        memberService.resign(memberId, reqDto);

        //then
        assertThat(findMember.getStatus()).isEqualTo(MemberStatus.RESIGN);
        assertThat(findMember.getExitDate().plusMinutes(1)).isAfter(LocalDateTime.now());
        verify(memberRepository, times(1)).findById(memberId);
        verify(passwordEncoder, times(1)).matches(reqDto.getPassword(), findMember.getPassword());
        verify(tokenService, times(1)).deleteRefreshToken(memberId, reqDto.getRefreshToken());

    }


    @Test
    public void 포인트적립내역() {
        //given
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member findMember = Member.create("test@test.com", "인코딩된 비밀번호",
                "닉네임","nickname", LocalDate.of(2022, 12, 12));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));
        findMember.getPointHistories().add(Point.createBoardPoint(findMember));
        findMember.getPointHistories().add(Point.createReplyPoint(findMember));
        findMember.getPointHistories().add(Point.createReviewPoint(findMember));
        findMember.getPointHistories().add(Point.createPaymentPoint(findMember, 100000));

        Page<Point> pointsPage = new PageImpl<>(findMember.getPointHistories(), pageable, 4);
        when(pointService.getPointHistory(findMember, pageable)).thenReturn(pointsPage);

        memberService.getPointHistory(memberId, pageable);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(pointService, times(1)).getPointHistory(findMember, pageable);
    }

    @Test
    public void 알람내역() {
        //given
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member findMember = Member.create("test@test.com", "인코딩된 비밀번호",
                "닉네임","nickname", LocalDate.of(2022, 12, 12));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(findMember));
        findMember.getAlarms().add(Alarm.createReplyAlarm(findMember, "writer1", "alarm1"));
        findMember.getAlarms().add(Alarm.createReplyAlarm(findMember, "writer2", "alarm2"));
        findMember.getAlarms().add(Alarm.createReplyAlarm(findMember, "writer3", "alarm3"));
        findMember.getAlarms().add(Alarm.createReplyAlarm(findMember, "writer4", "alarm4"));

        Page<Alarm> alarmsPage = new PageImpl<>(findMember.getAlarms(), pageable, 4);
        when(alarmService.getAlarmsToMember(findMember, pageable)).thenReturn(alarmsPage);

        memberService.getAlarms(memberId, pageable);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(alarmService, times(1)).getAlarmsToMember(findMember, pageable);
    }




}