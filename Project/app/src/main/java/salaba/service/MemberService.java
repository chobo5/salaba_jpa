package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.MemberJoinReqDto;
import salaba.dto.request.MemberModiReqDto;
import salaba.entity.Address;
import salaba.entity.Nation;
import salaba.entity.member.Member;
import salaba.exception.AlreadyExistsException;
import salaba.exception.PasswordValidationException;
import salaba.repository.MemberRepository;
import salaba.repository.NationRepository;
import salaba.util.Validator;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final NationRepository nationRepository;

    public boolean validateNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    public boolean validateEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    public Long join(MemberJoinReqDto memberDto) {
        if (!Validator.isValidPassword(memberDto.getPassword())) {
            throw new PasswordValidationException("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함한 8자 이상이여야 합니다.");
        }

        if (!validateNickname(memberDto.getNickname()) || !validateEmail(memberDto.getEmail())) {
            throw new AlreadyExistsException("이미 사용중인 이메일 또는 닉네임 입니다.");
        }

        Member newMember = Member.createMember(
                memberDto.getEmail(), memberDto.getPassword(), memberDto.getName() ,memberDto.getNickname(), memberDto.getBirthday());
        memberRepository.save(newMember);
        return newMember.getId();
    }

    public Long modifyProfile(MemberModiReqDto memberModiReqDto) {
        //회원이 없으면 예외 발생
        Member member = memberRepository.findById(memberModiReqDto.getId()).orElseThrow(NoSuchElementException::new);
        Nation nation = nationRepository.findById(memberModiReqDto.getNationId()).orElseThrow(NoSuchElementException::new);
        //entity를 변경하면 자동으로 반영
        member.changeProfile(memberModiReqDto.getName(), memberModiReqDto.getGender(), nation, new Address(memberModiReqDto.getStreet(), memberModiReqDto.getZipcode()));
        return member.getId();
    }

}
