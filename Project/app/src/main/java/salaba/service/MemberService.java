package salaba.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.MemberJoinDto;
import salaba.entity.member.Member;
import salaba.exception.PasswordValidationException;
import salaba.repository.MemberRepository;
import salaba.util.PasswordValidator;

import javax.xml.bind.ValidationException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Long join(MemberJoinDto memberDto) {
        if (!PasswordValidator.isValidPassword(memberDto.getPassword())) {
            throw new PasswordValidationException("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함한 8자 이상이여야 합니다.");
        }
        Member newMember = Member.createMember(
                memberDto.getEmail(), memberDto.getPassword(), memberDto.getName() ,memberDto.getNickname(), memberDto.getBirthday());
        memberRepository.save(newMember);
        return newMember.getId();
    }
}
