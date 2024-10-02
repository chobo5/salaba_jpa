package salaba.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.custom.MemberRepositoryCustom;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmailAndPassword(String email, String password);

}
