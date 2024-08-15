package salaba.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.dto.board.BoardDetailDto;
import salaba.dto.board.BoardDto;
import salaba.dto.board.QBoardDto;
import salaba.entity.board.*;

import java.util.ArrayList;
import java.util.List;

import static salaba.entity.board.QBoard.*;
import static salaba.entity.board.QBoardLike.*;
import static salaba.entity.board.QReply.*;
import static salaba.entity.member.QMember.*;

@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardDto> getList(BoardCategory category, Pageable pageable) {
        List<BoardDto> listResult = queryFactory.select(new QBoardDto(
                        board.id,
                        board.title,
                        board.content,
                        member.nickname,
                        board.viewCount,
                        board.createdDate,
                        boardLike.id.count().as("likeCount"),
                        reply.id.count().as("commentCount"))
                )
                .from(board)
                .join(board.writer, member)
                .join(boardLike).on(boardLike.board.eq(board))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.boardCategory.eq(category))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.id.count())
                .from(board)
                .where(board.boardCategory.eq(category));

        return PageableExecutionUtils.getPage(listResult, pageable, totalCount::fetchOne);

    }

//    @Override
//    public BoardDetailDto getOne(Long boardId) {
//        queryFactory.select(board, board.writer, comment, reply)
//                .from(board)
//                .join(board.writer, member)
//                .leftJoin(comment).on(comment.board.eq(board))
//                .leftJoin(reply).on(reply.comment.eq(comment))
//                .where(board.id.eq(boardId))
//                .fetch();
//
//    }
}
