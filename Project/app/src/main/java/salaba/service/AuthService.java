package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.*;
import salaba.dto.response.MemberLoginResDto;
import salaba.dto.response.TokenResDto;
import salaba.entity.member.Member;
import salaba.entity.member.MemberRole;
import salaba.entity.member.Role;
import salaba.exception.AlreadyExistsException;
import salaba.exception.PasswordNotCorrectException;
import salaba.repository.jpa.MemberRepository;
import salaba.repository.jpa.MemberRoleRepository;
import salaba.repository.jpa.RoleRepository;
import salaba.security.dto.RefreshTokenDto;
import salaba.util.RoleName;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public boolean isExistingNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }


    public boolean isExistingEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }


    public Long join(MemberJoinReqDto memberDto) {
        if (!isExistingNickname(memberDto.getNickname()) || !isExistingEmail(memberDto.getEmail())) {
            throw new AlreadyExistsException("이미 사용중인 이메일 또는 닉네임 입니다.");
        }

        //회원 생성
        Member newMember = Member.createMember(memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()),
                memberDto.getName() ,memberDto.getNickname(), memberDto.getBirthday());
        memberRepository.save(newMember);

        //일반 회원 권한 부여
        Role role = roleRepository.findByRoleName(RoleName.MEMBER).orElseThrow(NoSuchElementException::new);
        MemberRole memberRole = MemberRole.createMemberRole(newMember, role);
        memberRoleRepository.save(memberRole);
        return newMember.getId();
    }

    public MemberLoginResDto login(MemberLoginReqDto reqDto) {
        Optional<Member> findMember = memberRepository.findByEmail(reqDto.getEmail());

        if (findMember.isEmpty() || !passwordEncoder.matches(reqDto.getPassword(), findMember.get().getPassword())) {
            throw new NoSuchElementException("아이디 또는 비밀번호가 잘못 되었습니다");
        }
        TokenResDto tokens = tokenService.createTokens(findMember.get());

        return new MemberLoginResDto(tokens.getAccessToken(), tokens.getRefreshToken());
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


    public void logout(RefreshTokenDto refreshTokenDto) {
        tokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
    }
}
