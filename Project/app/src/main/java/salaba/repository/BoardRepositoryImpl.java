package salaba.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.dto.board.*;
import salaba.entity.board.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.list;
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
                        boardLike.board.id.countDistinct().as("likeCount"),
                        reply.id.countDistinct().as("replyCount"))
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

    @Override
    public BoardDetailDto get(Long boardId) {
        // likeCount 서브쿼리
        Expression<Long> likeCount = ExpressionUtils.as(JPAExpressions.select(boardLike.countDistinct())
                        .from(boardLike)
                        .where(boardLike.board.id.eq(boardId)),"likeCount");

        //board
        BoardDetailDto boardResult = queryFactory.select(Projections.constructor(BoardDetailDto.class,
                        board.id,
                        board.boardScope,
                        board.boardCategory,
                        board.title,
                        board.content,
                        member.id,
                        member.nickname,
                        board.viewCount,
                        likeCount,
                        board.createdDate))
                .from(board)
                .join(board.writer, member)
                .where(board.id.eq(boardId))
                .fetchOne();

        //댓글 리스트
        List<ReplyDto> replyDtoList = queryFactory.select(Projections.constructor(ReplyDto.class,
                        reply.id,
                        reply.board.id,
                        reply.writer.id,
                        reply.writer.nickname,
                        reply.content,
                        reply.createdDate))
                .from(reply)
                .where(reply.board.id.eq(boardId))
                .orderBy(reply.createdDate.desc())
                .fetch();

        //모든 대댓글 리스트
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
                                .where(parentReply.board.id.eq(boardId))))
                .orderBy(reply.createdDate.asc())
                .fetch();

        //부모 댓글 번호별로 그룹화
        Map<Long, List<ReplyToReplyDto>> groupedReReplyMap = reReplyList.stream()
                .collect(Collectors.groupingBy(ReplyToReplyDto::getParentId));

        replyDtoList.forEach(replyDto -> replyDto.setReplyToReplyList(groupedReReplyMap.get(replyDto.getId())));

        boardResult.setReplyList(replyDtoList);
        return boardResult;
    }


}
