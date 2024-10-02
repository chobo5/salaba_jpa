package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.auth.service.AuthService;
import salaba.domain.auth.service.TokenService;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.dto.response.AlarmResDto;
import salaba.domain.member.dto.response.PointResDto;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.global.entity.Address;
import salaba.global.entity.Nation;
import salaba.domain.member.repository.MemberRepository;
import salaba.global.repository.NationRepository;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final NationRepository nationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AlarmService alarmService;
    private final PointService pointService;
    private final AuthService authService;
    private final TokenService tokenService;


    public Long modifyProfile(Long memberId, MemberModiReqDto reqDto) {
        //회원이 없으면 예외 발생
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Nation nation = nationRepository.findById(reqDto.getNationId()).orElseThrow(NoSuchElementException::new);
        //entity를 변경하면 자동으로 반영
        Address address = reqDto.getStreet() != null && reqDto.getZipcode() != null ? new Address(reqDto.getStreet(), reqDto.getZipcode()) : null;

        member.changeProfile(reqDto.getName(), reqDto.getGender(), nation, address);
        return member.getId();
    }


    public void changePassword(Long memberId, ChangePasswordReqDto reqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);

        if (!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
        member.changePassword(passwordEncoder.encode(reqDto.getNewPassword()));
    }

    public void changeNickname(Long memberId, ChangeNicknameReqDto reqDto) {
        authService.isExistingNickname(reqDto.getNickname());
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changeNickname(reqDto.getNickname());
    }

    public void changeTelNo(Long memberId, ChangeTelNoReqDto reqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changeTelNo(reqDto.getTelNo());
    }

    public void resign(Long memberId, MemberResignReqDto reqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        if (!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
        tokenService.deleteRefreshToken(memberId, reqDto.getRefreshToken());
        member.resign();

    }

    public Page<PointResDto> getPointHistory(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Point> pointHistory = pointService.getPointHistory(member, pageable);

        return pointHistory.map(PointResDto::new);
    }

    public int getTotalPoint(Long memberId) {
        return pointService.getTotalPoint(memberId);
    }


    public Page<AlarmResDto> getAlarms(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Alarm> alarms = alarmService.getAlarmsToMember(member, pageable);
        return alarms.map(AlarmResDto::new);
    }



}
