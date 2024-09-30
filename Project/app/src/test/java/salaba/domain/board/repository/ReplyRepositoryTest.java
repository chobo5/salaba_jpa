package salaba.domain.board.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.entity.Board;
import salaba.domain.member.entity.Member;
import salaba.domain.board.entity.Reply;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class ReplyRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private ReplyRepository replyRepository;

    @BeforeEach
    public void init() {
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Member member2 = Member.create("test2@test.com", "Aa123456!@", "test2",
                "testNickname2", LocalDate.of(2000, 12, 12));
        em.persist(member);
        em.persist(member2);
        Board board = Board.create("test", "content", BoardScope.ALL, member);
        em.persist(board);
        Reply reply1 = Reply.createReply(board, "reply1", member2);
        replyRepository.save(reply1);
        Reply reply2 = Reply.createReply(board, "reply2", member2);
        replyRepository.save(reply2);
        Reply reply3 = Reply.createReply(board, "reply3", member2);
        replyRepository.save(reply3);

        Reply reReply1 = Reply.createReplyToReply(reply1, "reReply1", member2);
        replyRepository.save(reReply1);
        Reply reReply2 = Reply.createReplyToReply(reply2, "reReply2", member2);
        replyRepository.save(reReply2);
        Reply reReply3 = Reply.createReplyToReply(reply3, "reReply3", member2);
        replyRepository.save(reReply3);

    }

    @Test
    public void 댓글_작성자찾기() {
        //when
        List<Reply> replies = replyRepository.findAll();
        replies.forEach(System.out::println);
        Reply reply = replyRepository.findByIdWithWriter(replies.get(0).getId()).orElseThrow();

        //then
        assertThat(reply.getContent()).isEqualTo("reply1");
        assertThat(reply.getWriter().getNickname()).isEqualTo("testNickname2");

    }

}