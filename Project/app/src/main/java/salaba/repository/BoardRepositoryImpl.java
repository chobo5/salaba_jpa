package salaba.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.dto.board.BoardDto;
import salaba.dto.board.QBoardDto;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.QBoard;
import salaba.entity.board.QComment;

import java.util.List;

import static salaba.entity.board.QBoard.*;
import static salaba.entity.board.QBoardLike.*;
import static salaba.entity.board.QComment.*;
import static salaba.entity.member.QMember.*;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardDto> getList(BoardCategory category, Pageable pageable) {
        QBoard subBoard = new QBoard("b");
        QComment subComment = new QComment("q");
        List<BoardDto> listResult = queryFactory.select(new QBoardDto(
                        board.id,
                        board.title,
                        board.content,
                        member.nickname,
                        board.viewCount,
                        board.createdDate,
                        ExpressionUtils.as(JPAExpressions.select(boardLike.count())
                                .from(boardLike)
                                .where(board.eq(boardLike.board)), "likeCount"),
                        ExpressionUtils.as(JPAExpressions.select(comment.count())
                                .from(comment)
                                .where(comment.board.eq(board)), "commentCount"))
                )
                .from(board)
                .leftJoin(board.writer, member)
                .join(board, boardLike.board)
                .leftJoin(board, comment.board)
                .where(board.boardCategory.eq(category))
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.count())
                .from(board);

        return PageableExecutionUtils.getPage(listResult, pageable, totalCount::fetchOne);

    }
}
