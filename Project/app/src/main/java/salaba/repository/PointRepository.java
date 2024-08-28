package salaba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Member;
import salaba.entity.member.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findByMember(Member member, Pageable pageable);

    @Query("select sum(amount) from point where point.member_id = :memberId")
    int getTotalPoint(@Param(value = "memberId") Long memberId);
}
