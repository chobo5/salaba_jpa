package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
