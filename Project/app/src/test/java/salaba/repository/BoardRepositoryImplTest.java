package salaba.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.board.BoardDto;
import salaba.entity.board.BoardCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static salaba.entity.board.QBoard.board;
import static salaba.entity.board.QComment.comment;
import static salaba.entity.board.QReply.reply;
import static salaba.entity.member.QMember.member;

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

}