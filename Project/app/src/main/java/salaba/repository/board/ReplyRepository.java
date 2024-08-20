package salaba.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.board.Reply;
import salaba.entity.member.Member;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByWriter(Member member, Pageable pageable);
}
