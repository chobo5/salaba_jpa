package salaba.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Alarm;
import salaba.entity.member.Member;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByTargetMember(Member targetMember, Pageable pageable);
}
