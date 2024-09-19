package salaba.domain.board.repository;

import com.querydsl.core.Tuple;
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
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.BoardLike;
import salaba.domain.common.constants.WritingStatus;
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