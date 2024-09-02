package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.MemberJoinReqDto;
import salaba.dto.request.MemberLoginReqDto;
import salaba.dto.request.MemberResignReqDto;
import salaba.dto.response.MemberLoginResDto;
import salaba.entity.member.Member;
import salaba.entity.member.MemberRole;
import salaba.entity.member.Role;
import salaba.exception.AlreadyExistsException;
import salaba.repository.MemberRepository;
import salaba.repository.MemberRoleRepository;
import salaba.repository.RoleRepository;
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
    private final RefreshTokenService refreshTokenService;

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

        Map<String, String> tokens = refreshTokenService.createTokens(findMember.get());

        return new MemberLoginResDto(tokens.get("accessToken"), tokens.get("refreshToken"));
    }

    public void changePassword(Long memberId, String password) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changePassword(passwordEncoder.encode(password));
    }

    public void changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changeNickname(nickname);
    }

    public void changeTelNo(Long memberId, String telNo) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changePassword(telNo);
    }

    public void resign(MemberResignReqDto reqDto) {
        Member member = memberRepository.findByEmailAndPassword(reqDto.getEmail(), reqDto.getPassword()).orElseThrow(NoSuchElementException::new);
        member.resign();
    }



}
