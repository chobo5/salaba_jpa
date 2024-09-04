package salaba.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.MemberRole;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
