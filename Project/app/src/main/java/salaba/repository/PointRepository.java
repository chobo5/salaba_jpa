package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

}
