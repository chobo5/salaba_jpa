package salaba.domain.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.dto.response.QBoardResDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.BoardLike;
import salaba.domain.board.entity.QBoard;
import salaba.domain.board.entity.QBoardLike;
import salaba.domain.common.constants.WritingStatus;
import salaba.domain.member.entity.QMember;
import salaba.domain.reply.dto.response.ReplyResDto;
import salaba.domain.reply.dto.response.ReplyToReplyResDto;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.reply.entity.QReply;
import salaba.domain.reply.entity.Reply;
import salaba.domain.reply.repository.ReplyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static salaba.domain.board.entity.QBoard.board;
import static salaba.domain.board.entity.QBoardLike.boardLike;
import static salaba.domain.member.entity.QMember.member;
import static salaba.domain.reply.entity.QReply.reply;

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
    public void 게시물목록_querydsl() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        //when
        List<Tuple> result = queryFactory.select(board,
                        member,
                        boardLike.member.id.countDistinct().as("likeCount"),
                        reply.id.countDistinct().as("replyCount"))
                .from(board)
                .join(board.writer, member).fetchJoin()
                .join(boardLike).on(boardLike.board.eq(board)).fetchJoin()
                .leftJoin(reply).on(reply.board.eq(board)).fetchJoin()
                .where(board.writingStatus.eq(WritingStatus.NORMAL))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //then
        result.forEach(tuple -> {
            Board b = tuple.get(board);
            Long replyCount = tuple.get(reply.id.countDistinct().as("replyCount"));
            Long boardLikeCount = tuple.get(boardLike.member.id.countDistinct().as("likeCount"));
            System.out.println("board: " + b.getTitle() + b.getContent());
            System.out.println("board: " + b.getWriter().getNickname());
            System.out.println("replyCount: " + replyCount);
            System.out.println("boardLike: " + boardLikeCount);
        });

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

//    @Test
//    public void 게시물상세보기_querydsl() {
//        //given
//
//        //when
//        QReply reReply = new QReply("reReply");
//        QMember replyWriter = new QMember("replyWriter");
//        QMember reReplyWriter = new QMember("reReplyWriter");
//        List<Tuple> result = queryFactory.select(board, member, boardLike.member.id.countDistinct(), reply, replyWriter, reReply, reReplyWriter)
//                .from(board)
//                .join(member).on(board.writer.eq(member)).fetchJoin()
//                .leftJoin(boardLike).on(boardLike.board.eq(board)).fetchJoin()
//                .leftJoin(reply).on(reply.board.eq(board)).fetchJoin()
//                .join(replyWriter).on(replyWriter.eq(reply.writer)).fetchJoin()
//                .leftJoin(reReply).on(reReply.parent.eq(reply)).fetchJoin()
//                .join(reReplyWriter).on(reReplyWriter.eq(reReply.writer)).fetchJoin()
//                .where(reply.board.id.eq(boardId)
//                        .and(reply.writingStatus.eq(WritingStatus.NORMAL))
//                        .and(reReply.writingStatus.eq(WritingStatus.NORMAL)))
//                .orderBy(reply.createdDate.desc(), reReply.createdDate.desc())
//                .fetch();
//
//        result.forEach(tuple -> {
//            Board b = tuple.get(board);
//            Long likeCount = tuple.get(boardLike.member.id.countDistinct());
//            Member writer = tuple.get(member);
//            Reply r = tuple.get(reply);
//            Member rWriter = tuple.get(replyWriter);
//            Reply rr = tuple.get(reReply);
//            Member rrWriter = tuple.get(reReplyWriter);
//
//            System.out.println("------------------------------------------------");
//            System.out.println(likeCount);
//            System.out.println(b.getTitle() + " " + b.getContent());
//            System.out.println(writer.getNickname());
//            System.out.println(r.getId() + " " + r.getContent());
//            System.out.println(rWriter.getNickname());
//            System.out.println(rr.getId() + " " + rr.getContent());
//            System.out.println(rrWriter.getNickname());
//        });
//    }

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