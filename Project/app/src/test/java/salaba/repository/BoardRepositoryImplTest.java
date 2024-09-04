package salaba.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.board.BoardSearchReqDto;
import salaba.dto.response.BoardDetailResDto;
import salaba.dto.response.BoardResDto;
import salaba.dto.response.ReplyResDto;
import salaba.dto.response.ReplyToReplyResDto;
import salaba.entity.board.*;
import salaba.entity.member.Member;
import salaba.repository.jpa.MemberRepository;
import salaba.repository.jpa.board.BoardLikeRepository;
import salaba.repository.jpa.board.BoardRepository;
import salaba.repository.jpa.board.ReplyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static salaba.entity.board.QReply.reply;

@SpringBootTest
@Transactional
class BoardRepositoryImplTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Autowired
    ReplyRepository replyRepository;
    @PersistenceContext
    EntityManager em;

    Long boardId;

    JPAQueryFactory queryFactory;
    @BeforeEach
    public void 엔티티추가() {
        queryFactory = new JPAQueryFactory(em);

        Member member1 = Member.createMember("test1@test.com", "Aa1234567!@", "testname1",
                "testNickName1", LocalDate.of(1996, 10, 8));
        memberRepository.save(member1);

        Member member2 = Member.createMember("test2@test.com", "Aa1234567!@", "testname2",
                "testNickName2", LocalDate.of(1996, 10, 8));
        memberRepository.save(member2);

        Member member3 = Member.createMember("test3@test.com", "Aa1234567!@", "testname3",
                "testNickName3", LocalDate.of(1996, 10, 8));
        memberRepository.save(member3);

        Member member4 = Member.createMember("test4@test.com", "Aa1234567!@", "testname4",
                "testNickName4", LocalDate.of(1996, 10, 8));
        memberRepository.save(member4);

        Member member5 = Member.createMember("test5@test.com", "Aa1234567!@", "testname5",
                "testNickName5", LocalDate.of(1996, 10, 8));
        memberRepository.save(member5);

        Board board  = Board.createBoard("test title", "test content", BoardScope.ALL, member1);
        boardRepository.save(board);
        boardId = board.getId();

        Board board2  = Board.createBoard("test title2", "test content2", BoardScope.ALL, member2);
        boardRepository.save(board2);


        Reply reply1 = Reply.createReply(board, "reply1", member1);
        replyRepository.save(reply1);
        Reply reply1_1 = Reply.createReplyToReply(reply1, "reply1_1", member2);
        replyRepository.save(reply1_1);

        Reply reply2 = Reply.createReply(board, "reply2", member2);
        replyRepository.save(reply2);
        Reply reply2_1 = Reply.createReplyToReply(reply2, "reply2_1", member1);
        Reply reply2_2 = Reply.createReplyToReply(reply2, "reply2_2", member2);
        replyRepository.save(reply2_1);
        replyRepository.save(reply2_2);


        Reply reply3 = Reply.createReply(board, "reply3", member3);
        replyRepository.save(reply3);
        Reply reply3_1 = Reply.createReplyToReply(reply3, "reply3_1", member1);
        Reply reply3_2 = Reply.createReplyToReply(reply3, "reply3_2", member2);
        Reply reply3_3 = Reply.createReplyToReply(reply3, "reply3_3", member3);
        replyRepository.save(reply3_1);
        replyRepository.save(reply3_2);
        replyRepository.save(reply3_3);

        boardLikeRepository.save(BoardLike.createBoardLike(board, member1));
        boardLikeRepository.save(BoardLike.createBoardLike(board, member2));
        boardLikeRepository.save(BoardLike.createBoardLike(board, member3));
        boardLikeRepository.save(BoardLike.createBoardLike(board, member4));
        boardLikeRepository.save(BoardLike.createBoardLike(board, member5));

        boardLikeRepository.save(BoardLike.createBoardLike(board2, member3));
        boardLikeRepository.save(BoardLike.createBoardLike(board2, member4));
        boardLikeRepository.save(BoardLike.createBoardLike(board2, member5));

    }

    @Test
    public void 게시물목록() {
        //given

        //when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardResDto> list = boardRepository.getList(pageRequest);
//        System.out.println(list.getContent());

        //then
        assertThat(list.getTotalElements()).isEqualTo(2);
        assertThat(list.getTotalPages()).isEqualTo(1);

        assertThat(list.getContent().get(0).getLikeCount()).isEqualTo(3);
        assertThat(list.getContent().get(0).getReplyCount()).isEqualTo(0);

        assertThat(list.getContent().get(1).getLikeCount()).isEqualTo(5);
        assertThat(list.getContent().get(1).getReplyCount()).isEqualTo(3);
        assertThat(list.getContent().get(1).getBoardId()).isEqualTo(boardId);

    }

    @Test
    public void 게시물상세보기() {
        //given

        //when
        BoardDetailResDto result = boardRepository.get(boardId).orElseThrow(NoSuchElementException::new);

        //then
        assertThat(result.getContent()).isEqualTo("test content");
        assertThat(result.getLikeCount()).isEqualTo(5);
        assertThat(result.getReplyList().size()).isEqualTo(3);
        System.out.println(result);
        for (int i = 0; i <= 2 ; i++) {
            assertThat(result.getReplyList().get(i).getReplyToReplyList().size()).isEqualTo(3 - i);
        }
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
    public void replyListTest() {
        List<ReplyResDto> replyResDtoList = queryFactory.select(Projections.constructor(ReplyResDto.class,
                        reply.id,
                        reply.board.id,
                        reply.writer.id,
                        reply.writer.nickname,
                        reply.content,
                        reply.createdDate))
                .from(reply)
                .where(reply.board.id.eq(24L))
                .orderBy(reply.createdDate.desc())
                .fetch();

        replyResDtoList.forEach(System.out::println);
    }

    @Test
    public void replyToReplyListTest() {
        QReply parentReply = new QReply("parent");
        List<ReplyToReplyResDto> reReplyList = queryFactory.select(Projections.constructor(ReplyToReplyResDto.class,
                        reply.id,
                        reply.parent.id,
                        reply.writer.id,
                        reply.writer.nickname,
                        reply.content,
                        reply.createdDate))
                .from(reply)
                .where(reply.parent.id.in(
                        JPAExpressions
                                .select(parentReply.id)
                                .from(parentReply)
                                .where(parentReply.board.id.eq(24L))))
                .orderBy(reply.createdDate.desc())
                .fetch();
        reReplyList.forEach(System.out::println);
    }

}