package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.dto.response.AlarmResDto;
import salaba.domain.member.dto.response.PointResDto;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.common.repository.NationRepository;
import salaba.exception.PasswordNotCorrectException;

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


    public Long modifyProfile(Long memberId, MemberModiReqDto memberModiReqDto) {
        //회원이 없으면 예외 발생
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Nation nation = nationRepository.findById(memberModiReqDto.getNationId()).orElseThrow(NoSuchElementException::new);
        //entity를 변경하면 자동으로 반영
        member.changeProfile(memberModiReqDto.getName(), memberModiReqDto.getGender(), nation, new Address(memberModiReqDto.getStreet(), memberModiReqDto.getZipcode()));
        return member.getId();
    }


    public void changePassword(Long memberId, ChangePasswordReqDto reqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        if (!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new PasswordNotCorrectException("비밀번호가 일치하지 않습니다.");
        }
        member.changePassword(passwordEncoder.encode(reqDto.getNewPassword()));
    }

    public void changeNickname(Long memberId, ChangeNicknameReqDto reqDto) {
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
            throw new PasswordNotCorrectException("비밀번호가 일치하지 않습니다.");
        }
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
