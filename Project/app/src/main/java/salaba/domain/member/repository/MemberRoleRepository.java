package salaba.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.auth.entity.MemberRole;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
