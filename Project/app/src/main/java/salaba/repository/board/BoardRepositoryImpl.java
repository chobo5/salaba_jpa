package salaba.repository.board;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import salaba.dto.request.BoardSearchReqDto;
import salaba.dto.request.board.*;
import salaba.entity.board.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .where(board.boardCategory.eq(category)
                        .and(board.writingStatus.eq(WritingStatus.NORMAL)))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.id.count())
                .from(board)
                .where(board.boardCategory.eq(category)
                        .and(board.writingStatus.eq(WritingStatus.NORMAL)));

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
                .where(board.id.eq(boardId)
                        .and(board.writingStatus.eq(WritingStatus.NORMAL)))
                .fetchOne();

        //댓글 리스트
        List<ReplyDto> replyDtoList = queryFactory.select(Projections.constructor(ReplyDto.class,
                        reply.id,
                        reply.board.id,
                        reply.writer.id,
                        reply.writer.nickname,
                        Expressions.cases().when(reply.writingStatus.eq(WritingStatus.DELETED)).then("삭제된 댓글입니다.").otherwise(reply.content),
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
                                .where(parentReply.board.id.eq(boardId)))
                        .and(reply.writingStatus.eq(WritingStatus.NORMAL)))
                .orderBy(reply.createdDate.asc())
                .fetch();

        //부모 댓글 번호별로 그룹화
        Map<Long, List<ReplyToReplyDto>> groupedReReplyMap = reReplyList.stream()
                .collect(Collectors.groupingBy(ReplyToReplyDto::getParentId));

        replyDtoList.forEach(replyDto -> replyDto.setReplyToReplyList(groupedReReplyMap.get(replyDto.getId())));

        boardResult.setReplyList(replyDtoList);
        return boardResult;
    }

    public Page<BoardDto> search(BoardCategory boardCategory, BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
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
                .where(board.boardCategory.eq(boardCategory)
                        .and(board.writingStatus.eq(WritingStatus.NORMAL)),
                        boardTitleLike(boardSearchReqDto.getTitle()),
                        boardWriterLike(boardSearchReqDto.getWriter()))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.id.count())
                .from(board)
                .where(board.boardCategory.eq(boardCategory).and(board.writingStatus.eq(WritingStatus.NORMAL)),
                        boardTitleLike(boardSearchReqDto.getTitle()),
                        boardWriterLike(boardSearchReqDto.getWriter()));

        return PageableExecutionUtils.getPage(listResult, pageable, totalCount::fetchOne);
    }

    private BooleanExpression boardTitleLike(String title) {
        return title != null ? board.title.contains(title) : null;
    }

    private BooleanExpression boardWriterLike(String writer) {
        return writer != null ? board.writer.nickname.contains(writer) : null;
    }


}
