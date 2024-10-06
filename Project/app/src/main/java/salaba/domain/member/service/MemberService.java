package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.auth.service.AuthService;
import salaba.domain.auth.service.TokenService;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.dto.response.AlarmResDto;
import salaba.domain.member.dto.response.PointResDto;
import salaba.domain.member.entity.Alarm;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Nation;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.global.repository.NationRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

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
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));


        Nation nation = reqDto.getNationId() != null ? nationRepository.findById(reqDto.getNationId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Nation.class, reqDto.getNationId()))) : null;

        //entity를 변경하면 자동으로 반영
        Address address = reqDto.getStreet() != null && reqDto.getZipcode() != null ? new Address(reqDto.getStreet(), reqDto.getZipcode()) : null;

        member.changeProfile(reqDto.getName(), reqDto.getGender(), nation, address);
        return member.getId();
    }


    public void changePassword(Long memberId, ChangePasswordReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        if (!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
        member.changePassword(passwordEncoder.encode(reqDto.getNewPassword()));
    }

    public void changeNickname(Long memberId, ChangeNicknameReqDto reqDto) {
        authService.isExistingNickname(reqDto.getNickname());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        member.changeNickname(reqDto.getNickname());
    }

    public void changeTelNo(Long memberId, ChangeTelNoReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        member.changeTelNo(reqDto.getTelNo());
    }

    public void resign(Long memberId, MemberResignReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        if (!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }
        tokenService.deleteRefreshToken(memberId, reqDto.getRefreshToken());
        member.resign();

    }

    public Page<PointResDto> getPointHistory(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Page<Point> pointHistory = pointService.getPointHistory(member, pageable);

        return pointHistory.map(PointResDto::new);
    }

    public int getTotalPoint(Long memberId) {
        return pointService.getTotalPoint(memberId);
    }


    public Page<AlarmResDto> getAlarms(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Page<Alarm> alarms = alarmService.getAlarmsToMember(member, pageable);
        return alarms.map(AlarmResDto::new);
    }



}
