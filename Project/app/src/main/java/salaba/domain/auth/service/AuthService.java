package salaba.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.auth.constant.MemberStatus;
import salaba.domain.auth.constant.RoleName;
import salaba.domain.auth.dto.request.MemberJoinReqDto;
import salaba.domain.auth.dto.request.MemberLoginReqDto;
import salaba.domain.auth.dto.response.TokenResDto;
import salaba.domain.auth.exception.BlockedAccountException;
import salaba.domain.auth.exception.ResignedAccountException;
import salaba.domain.auth.exception.SleepingAccountException;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.auth.entity.MemberRole;
import salaba.domain.auth.entity.Role;
import salaba.domain.auth.dto.response.MemberLoginResDto;
import salaba.domain.member.exception.AlreadyExistsException;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.repository.MemberRoleRepository;
import salaba.domain.member.repository.RoleRepository;
import salaba.domain.auth.dto.RefreshTokenDto;
import salaba.domain.member.repository.query.MemberQueryRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
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
        if (memberQueryRepository.findByEmail(email).isEmpty()) {
            return;
        }
        throw new AlreadyExistsException("이미 사용중인 이메일 입니다.");
    }


    public Long join(MemberJoinReqDto reqDto) {
        //존재하면 에러 발생
        isExistingNickname(reqDto.getNickname());
        isExistingEmail(reqDto.getEmail());

        //회원 생성
        Member newMember = Member.create(reqDto.getEmail(), passwordEncoder.encode(reqDto.getPassword()),
                reqDto.getName(), reqDto.getNickname(), reqDto.getBirthday());
        memberRepository.save(newMember);

        //일반 회원 권한 부여
        Role role = roleRepository.findByRoleName(RoleName.MEMBER)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Role.class, "MEMBER")));
        MemberRole memberRole = MemberRole.create(newMember, role);
        memberRoleRepository.save(memberRole);
        return newMember.getId();
    }

    public MemberLoginResDto login(MemberLoginReqDto reqDto) {
        Optional<Member> findMember = memberQueryRepository.findByEmail(reqDto.getEmail());

        if (findMember.isEmpty() || !passwordEncoder.matches(reqDto.getPassword(), findMember.get().getPassword())) {
            throw new ValidationException("아이디 또는 비밀번호가 잘못 되었습니다");
        }
        Member existingMember = findMember.get();

        if (existingMember.getStatus() != MemberStatus.NORMAL) {
            throwAccountErrorByStatus(existingMember.getStatus());
        }

        existingMember.login();
        TokenResDto tokens = tokenService.createTokens(existingMember);

        return new MemberLoginResDto(tokens.getAccessToken(), tokens.getRefreshToken());
    }

    public void logout(Long memberId, RefreshTokenDto refreshTokenDto) {
        tokenService.deleteRefreshToken(memberId, refreshTokenDto.getRefreshToken());
    }

    private void throwAccountErrorByStatus(MemberStatus status) {
        switch (status) {
            case SLEEP -> throw new SleepingAccountException("계정이 휴면 상태입니다.");

            case BLOCKED -> throw new BlockedAccountException("차단된 계정입니다.");

            case RESIGN -> throw new ResignedAccountException("탈퇴한 계정입니다.");
        }
    }



}
