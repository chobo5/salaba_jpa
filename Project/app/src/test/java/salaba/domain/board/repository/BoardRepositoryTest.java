package salaba.domain.board.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import salaba.config.QuerydslConfig;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.BoardLike;
import salaba.domain.global.constants.WritingStatus;
import salaba.domain.member.entity.Member;
import salaba.domain.board.entity.Reply;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;
    
    @Test
    public void 게시물목록() {
        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<BoardResDto> boards = boardRepository.getList(pageable);
        assertThat(boards.getTotalElements()).isEqualTo(2);
        assertThat(boards.getContent().size()).isEqualTo(2);
        assertThat(boards.getContent().get(0).getReplyCount()).isEqualTo(3);
        assertThat(boards.getContent().get(0).getLikeCount()).isEqualTo(5);
        assertThat(boards.getContent().get(1).getReplyCount()).isEqualTo(0);
        assertThat(boards.getContent().get(1).getLikeCount()).isEqualTo(3);
    }

    @Test
    public void 게시물상세() {
        //when
        List<Board> boards = boardRepository.findAll();
        BoardDetailResDto board = boardRepository.get(boards.get(0).getId()).get();

        //then
        assertThat(board.getReplyList().size()).isEqualTo(3);
        assertThat(board.getViewCount()).isEqualTo(1);
        assertThat(board.getLikeCount()).isEqualTo(5);
        assertThat(board.getReplyList().get(0).getReplyToReplyList().size()).isEqualTo(1);
        assertThat(board.getReplyList().get(1).getReplyToReplyList().size()).isEqualTo(2);
        assertThat(board.getReplyList().get(2).getReplyToReplyList().size()).isEqualTo(3);

    }

    @Test
    public void 게시물상세2() {
        //when
        BoardDetailResDto board = boardRepository.get(2L).get();

        //then
        assertThat(board.getViewCount()).isEqualTo(1);
        assertThat(board.getLikeCount()).isEqualTo(3);
        assertThat(board.getReplyList()).isEmpty();

    }

    @Test
    public void 회원의게시글목록() {
        //given
        Member member = em.createQuery("select m from Member m", Member.class).getResultList().get(0);
        Pageable pageable = PageRequest.of(0, 10);

        //given
        Page<Board> boards = boardRepository.findByWriterAndWritingStatus(member, WritingStatus.NORMAL, pageable);

        //then
        assertThat(boards.getTotalElements()).isEqualTo(1);
        assertThat(boards.getContent().size()).isEqualTo(1);
    }

    @Test
    public void 게시물검색_게시물제목() {
        //given
        BoardSearchReqDto reqDto = new BoardSearchReqDto();
        reqDto.setTitle("2");
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<BoardResDto> result = boardRepository.search(reqDto, pageable);

        //then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo("test content2");

    }

    @Test
    public void 게시물검색_작성자닉네임() {
        //given
        BoardSearchReqDto reqDto = new BoardSearchReqDto();
        reqDto.setWriter("2");
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<BoardResDto> result = boardRepository.search(reqDto, pageable);

        //then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo("test content2");
    }

    @Test
    public void 게시물과작성자가져오기() {
        //given
        Member member = em.createQuery("select m from Member m", Member.class).getResultList().get(0);
        List<Board> boards = boardRepository.findAll();
        //when
        Board board = boardRepository.findByIdWithWriter(boards.get(0).getId()).get();

        //then
        assertThat(board.getWriter()).isEqualTo(member);
    }


    @BeforeEach
    public void 데이터생성() {
        Member member1 = Member.create("test1@test.com", "Aa1234567!@", "testname1",
                "testNickName1", LocalDate.of(1996, 10, 8));
        em.persist(member1);

        Member member2 = Member.create("test2@test.com", "Aa1234567!@", "testname2",
                "testNickName2", LocalDate.of(1996, 10, 8));
        em.persist(member2);

        Member member3 = Member.create("test3@test.com", "Aa1234567!@", "testname3",
                "testNickName3", LocalDate.of(1996, 10, 8));
        em.persist(member3);

        Member member4 = Member.create("test4@test.com", "Aa1234567!@", "testname4",
                "testNickName4", LocalDate.of(1996, 10, 8));
        em.persist(member4);

        Member member5 = Member.create("test5@test.com", "Aa1234567!@", "testname5",
                "testNickName5", LocalDate.of(1996, 10, 8));
        em.persist(member5);

        Board board = Board.create("test title", "test content", BoardScope.ALL, member1);
        em.persist(board);

        Board board2  = Board.create("test title2", "test content2", BoardScope.ALL, member2);
        em.persist(board2);


        Reply reply1 = Reply.createReply(board, "reply1", member1);
        em.persist(reply1);
        Reply reply1_1 = Reply.createReplyToReply(reply1, "reply1_1", member2);
        em.persist(reply1_1);

        Reply reply2 = Reply.createReply(board, "reply2", member2);
        em.persist(reply2);
        Reply reply2_1 = Reply.createReplyToReply(reply2, "reply2_1", member1);
        Reply reply2_2 = Reply.createReplyToReply(reply2, "reply2_2", member2);
        em.persist(reply2_1);
        em.persist(reply2_2);


        Reply reply3 = Reply.createReply(board, "reply3", member3);
        em.persist(reply3);
        Reply reply3_1 = Reply.createReplyToReply(reply3, "reply3_1", member1);
        Reply reply3_2 = Reply.createReplyToReply(reply3, "reply3_2", member2);
        Reply reply3_3 = Reply.createReplyToReply(reply3, "reply3_3", member3);
        em.persist(reply3_1);
        em.persist(reply3_2);
        em.persist(reply3_3);

        em.persist(BoardLike.create(board, member1));
        em.persist(BoardLike.create(board, member2));
        em.persist(BoardLike.create(board, member3));
        em.persist(BoardLike.create(board, member4));
        em.persist(BoardLike.create(board, member5));

        em.persist(BoardLike.create(board2, member3));
        em.persist(BoardLike.create(board2, member4));
        em.persist(BoardLike.create(board2, member5));
    }

}