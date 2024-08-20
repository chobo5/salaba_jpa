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
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.board.BoardDto;
import salaba.dto.request.board.ReplyDto;
import salaba.dto.request.board.ReplyToReplyDto;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.QReply;
import salaba.repository.board.BoardRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static salaba.entity.board.QReply.reply;

@SpringBootTest
@Transactional
class BoardRepositoryImplTest {
    @Autowired
    BoardRepository boardRepository;
    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;
    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void boardListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardDto> list = boardRepository.getList(BoardCategory.FREE, pageRequest);
        list.forEach(System.out::println);

    }

//    @Test
//    public void boardTest() {
//        List<Tuple> result = queryFactory.select(board, board.writer, reply)
//                .from(board)
//                .join(board.writer, member)
//                .leftJoin(reply).on(reply.board.eq(reply))
//                .leftJoin(reply).on(reply.comment.eq(comment))
//                .where(board.id.eq(10L))
//                .fetch();
//
//        result.forEach(System.out::println);
//    }

    @Test
    public void replyListTest() {
        List<ReplyDto> replyDtoList = queryFactory.select(Projections.constructor(ReplyDto.class,
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

        replyDtoList.forEach(System.out::println);
    }

    @Test
    public void replyToReplyListTest() {
        QReply parentReply = new QReply("parent");
        List<ReplyToReplyDto> reReplyList = queryFactory.select(Projections.constructor(ReplyToReplyDto.class,
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