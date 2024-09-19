package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.constants.RoleName;
import salaba.domain.member.dto.request.*;
import salaba.domain.member.dto.response.TokenResDto;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.MemberRole;
import salaba.domain.member.entity.Role;
import salaba.domain.member.dto.response.MemberLoginResDto;
import salaba.exception.AlreadyExistsException;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.repository.MemberRoleRepository;
import salaba.domain.member.repository.RoleRepository;
import salaba.domain.member.dto.RefreshTokenDto;

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

    public void isExistingNickname(String nickname) {
        if (memberRepository.findByNickname(nickname).isEmpty()) {
            return;
        }
        throw new AlreadyExistsException("이미 사용중인 닉네임 입니다.");
    }


    public void isExistingEmail(String email) {
        if (memberRepository.findByEmail(email).isEmpty()) {
            return;
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }


    public Long join(MemberJoinReqDto reqDto) {
        //존재하면 에러 발생
        isExistingNickname(reqDto.getNickname());
        isExistingEmail(reqDto.getEmail());

        //회원 생성
        Member newMember = Member.createMember(reqDto.getEmail(), passwordEncoder.encode(reqDto.getPassword()),
                reqDto.getName(), reqDto.getNickname(), reqDto.getBirthday());
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
        Member verifiedMember = findMember.get();
        verifiedMember.login();
        TokenResDto tokens = tokenService.createTokens(verifiedMember);

        return new MemberLoginResDto(tokens.getAccessToken(), tokens.getRefreshToken());
    }

    public void logout(Long memberId, RefreshTokenDto refreshTokenDto) {
        tokenService.deleteRefreshToken(memberId, refreshTokenDto.getRefreshToken());
    }



}
