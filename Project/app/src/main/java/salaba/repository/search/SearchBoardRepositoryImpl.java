package salaba.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import salaba.entity.*;
import salaba.entity.board.Board;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    /*
    "SELECT bc, bh, b, w, count(c), count(bl) " +
            "FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN b.boardHead bh " +
            "LEFT JOIN b.boardCategory bc " +
            "LEFT JOIN Comment c ON c.board = b " +
            "LEFT JOIN BoardLike bl ON bl.board = b " +
            "GROUP BY b " +
            "HAVING b.boardCategory.categoryNo = :categoryNo " +
            "AND b.state = '0'"
     */
    @Override
    public Board search1(int categoryNo) {
        log.info("search1......................");

        QBoard board = QBoard.board;
        QBoardHead boardHead = QBoardHead.boardHead;
        QBoardCategory boardCategory = QBoardCategory.boardCategory;
        QMember writer = QMember.member;
        QComment comment = QComment.comment;
        QBoardLike boardLike = QBoardLike.boardLike;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(boardHead).on(board.boardHead.eq(boardHead));
        jpqlQuery.leftJoin(boardCategory).on(board.boardCategory.eq(boardCategory));
        jpqlQuery.leftJoin(writer).on(writer.eq(board.writer));
        jpqlQuery.leftJoin(comment).on(comment.board.eq(board));
        jpqlQuery.leftJoin(boardLike).on(boardLike.board.eq(board));


        JPQLQuery<Tuple> tuple = jpqlQuery.select(boardHead, boardCategory, writer, comment.count(), boardLike.count())
                .where(board.boardNo.eq(11L))
                .groupBy(board);
//                .having(board.boardCategory.categoryNo.eq(categoryNo), board.state.eq('0'));

        log.info("-----------------------------------------");
        log.info(tuple);
        log.info("-----------------------------------------");

        List<Tuple> result = tuple.fetch();
        log.info("-----------------------------------------");
        log.info(result);
        log.info("-----------------------------------------");
        return null;
    }

    /*
    "SELECT bc, bh, bs, b, bw, c, cw, r, rw " +
            "FROM Board b " +
            "LEFT JOIN b.writer bw " +
            "LEFT JOIN b.boardCategory bc " +
            "LEFT JOIN b.boardHead bh " +
            "LEFT JOIN b.boardScope bs " +
            "LEFT JOIN Comment c ON c.board = b " +
            "LEFT JOIN c.writer cw " +
            "LEFT JOIN Reply r ON r.comment = c " +
            "LEFT JOIN r.writer rw " +
            "WHERE b.boardNo = :boardNo "
     */
    @Override
    public Board search2() {
        QBoard board = QBoard.board;
        QBoardHead boardHead = QBoardHead.boardHead;
        QBoardCategory boardCategory = QBoardCategory.boardCategory;
        QBoardScope boardScope = QBoardScope.boardScope;
        QMember writer = QMember.member;
        QComment comment = QComment.comment;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(boardHead).on(board.boardHead.eq(boardHead))
                .leftJoin(boardCategory).on(board.boardCategory.eq(boardCategory))
                .leftJoin(boardScope).on(board.boardScope.eq(boardScope))
                .leftJoin(writer).on(board.writer.eq(writer))
                .leftJoin(comment).on(comment.board.eq(board))
                .leftJoin(reply).on(reply.comment.eq(comment));
        return null;
    }

    @Override
    public Page<Object[]> searchBoard(String type, String keyword, Pageable pageable) {
        log.info("searchBoard..........");
        QBoard board = QBoard.board;
        QBoardHead boardHead = QBoardHead.boardHead;
        QBoardCategory boardCategory = QBoardCategory.boardCategory;
        QMember writer = QMember.member;
        QComment comment = QComment.comment;
        QBoardLike boardLike = QBoardLike.boardLike;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(boardHead).on(board.boardHead.eq(boardHead));
        jpqlQuery.leftJoin(boardCategory).on(board.boardCategory.eq(boardCategory));
        jpqlQuery.leftJoin(writer).on(writer.eq(board.writer));
        jpqlQuery.leftJoin(comment).on(comment.board.eq(board));
        jpqlQuery.leftJoin(boardLike).on(boardLike.board.eq(board));


        JPQLQuery<Tuple> tuple = jpqlQuery.select(boardHead, boardCategory, writer, comment.count(), boardLike.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        BooleanExpression expression;
        if (type.equals("w")) {
            expression = board.writer.nickname.contains(keyword);
        } else if (type.equals("c")) {
            expression = board.content.contains(keyword);
        } else {
            expression = board.title.contains(keyword);
        }
        booleanBuilder.and(expression);


        tuple.where(booleanBuilder);
        tuple.groupBy(board);
        this.getQuerydsl().applyPagination(pageable, tuple);
        List<Tuple> result = tuple.fetch();
        log.info(result);
        long count = tuple.fetchCount();
        log.info("Count: " + count);
        return new PageImpl<Object[]>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }
}
