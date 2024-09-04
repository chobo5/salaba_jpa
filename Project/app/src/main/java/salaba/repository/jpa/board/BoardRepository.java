package salaba.repository.jpa.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.board.Board;
import salaba.entity.member.Member;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Page<Board> findByWriter(Member member, Pageable pageable);
}
