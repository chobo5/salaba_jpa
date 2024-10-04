package salaba.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.board.entity.Board;
import salaba.domain.board.repository.custom.BoardRepositoryCustom;
import salaba.domain.global.constants.WritingStatus;
import salaba.domain.member.entity.Member;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Page<Board> findByWriterAndWritingStatus(Member member, WritingStatus status, Pageable pageable);
}
