package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.MemberJoinReqDto;
import salaba.dto.request.MemberLoginReqDto;
import salaba.dto.request.MemberModiReqDto;
import salaba.dto.request.ReviewReqDto;
import salaba.dto.response.AlarmResDto;
import salaba.dto.response.PointResDto;
import salaba.entity.Address;
import salaba.entity.Nation;
import salaba.entity.RefreshToken;
import salaba.entity.member.*;
import salaba.entity.rental.Reservation;
import salaba.entity.rental.Review;
import salaba.exception.AlreadyExistsException;
import salaba.repository.*;
import salaba.repository.rentalHome.ReservationRepository;
import salaba.repository.rentalHome.ReviewRepository;
import salaba.security.dto.MemberLoginResponseDto;
import salaba.security.dto.RefreshTokenDto;
import salaba.security.jwt.util.JwtTokenizer;
import salaba.util.RestResult;
import salaba.util.RoleName;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final NationRepository nationRepository;
    private final PointRepository pointRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final AlarmRepository alarmRepository;
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
        Member newMember = Member.createMember(memberDto.getEmail(), memberDto.getPassword(),
                memberDto.getName() ,memberDto.getNickname(), memberDto.getBirthday());
        memberRepository.save(newMember);

        //일반 회원 권한 부여
        Role role = roleRepository.findByRoleName(RoleName.MEMBER).orElseThrow(NoSuchElementException::new);
        MemberRole memberRole = MemberRole.createMemberRole(newMember, role);
        memberRoleRepository.save(memberRole);
        return newMember.getId();
    }

    public MemberLoginResponseDto login(String email, String password) {
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isEmpty() || !passwordEncoder.matches(password, findMember.get().getPassword())) {
            throw new NoSuchElementException("아이디 또는 비밀번호가 잘못 되었습니다");
        }

        Map<String, String> tokens = refreshTokenService.createTokens(findMember.get());


        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .build();

    }

    public Long modifyProfile(MemberModiReqDto memberModiReqDto) {
        //회원이 없으면 예외 발생
        Member member = memberRepository.findById(memberModiReqDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Nation nation = nationRepository.findById(memberModiReqDto.getNationId()).orElseThrow(NoSuchElementException::new);
        //entity를 변경하면 자동으로 반영
        member.changeProfile(memberModiReqDto.getName(), memberModiReqDto.getGender(), nation, new Address(memberModiReqDto.getStreet(), memberModiReqDto.getZipcode()));
        return member.getId();
    }

    public Page<PointResDto> getPointHistory(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Point> pointHistory = pointRepository.findByMember(member, pageable);

        return pointHistory.map(PointResDto::new);
    }

    public int getTotalPoint(Long memberId) {
        return pointRepository.getTotalPoint(memberId);
    }

    public Long createReview(ReviewReqDto reviewReqDto) {
        Reservation reservation = reservationRepository.findByIdWithMember(reviewReqDto.getReservationId()).orElseThrow(NoSuchElementException::new);
        Review review = Review.createReview(reservation, reviewReqDto.getScore(), reviewReqDto.getContent());
        reviewRepository.save(review);

        Point point = Point.createReviewPoint(reservation.getMember());
        pointRepository.save(point);

        return review.getId();
    }

    public Page<AlarmResDto> getAlarms(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Alarm> alarms = alarmRepository.findByTargetMember(member, pageable);
        return alarms.map(AlarmResDto::new);
    }


    public void changePassword(Long memberId, String password) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changePassword(password);
    }

    public void changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changeNickname(nickname);
    }

    public void changeTelNo(Long memberId, String telNo) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        member.changePassword(telNo);
    }

    public void resign(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password).orElseThrow(NoSuchElementException::new);
        member.resign();
    }
}
