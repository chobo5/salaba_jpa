package salaba.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.repository.query.MemberQueryRepository;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Scheduled(cron = "0 0 4 * * ?")
    public void updateSleeperAccounts() {
        memberQueryRepository.updateMemberWhereLastLoginDateIsBeforeAYear();
    }

    public void awakeSleeperAccount(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));
        member.awake();
    }
}
