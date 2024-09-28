package salaba.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import salaba.domain.board.entity.Reply;
import salaba.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByWriter(Member member, Pageable pageable);

    @Query("select r from Reply r join fetch r.writer w where r.id = :replyId")
    Optional<Reply> findByIdWithWriter(@Param("replyId") Long replyId);
}
