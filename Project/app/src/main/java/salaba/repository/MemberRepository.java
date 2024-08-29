package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.member.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

}
