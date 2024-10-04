package salaba.domain.board.repository.custom.impl;

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
import org.springframework.stereotype.Repository;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.dto.response.QBoardResDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.QReply;
import salaba.domain.board.repository.custom.BoardRepositoryCustom;
import salaba.domain.global.constants.WritingStatus;
import salaba.domain.board.dto.response.ReplyResDto;
import salaba.domain.board.dto.response.ReplyToReplyResDto;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static salaba.domain.board.entity.QBoard.board;
import static salaba.domain.board.entity.QBoardLike.boardLike;
import static salaba.domain.board.entity.QReply.reply;
import static salaba.domain.member.entity.QMember.member;


@RequiredArgsConstructor
@Slf4j
@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardResDto> getList(Pageable pageable) {
        List<BoardResDto> listResult = queryFactory.select(new QBoardResDto(
                        board.id,
                        board.title,
                        board.content,
                        member.nickname,
                        board.viewCount,
                        board.createdDate,
                        boardLike.member.id.countDistinct().as("likeCount"),
                        reply.id.countDistinct().as("replyCount"))
                )
                .from(board)
                .join(board.writer, member)
                .leftJoin(boardLike).on(boardLike.board.eq(board))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.writingStatus.eq(WritingStatus.NORMAL))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.id.count())
                .from(board)
                .where(board.writingStatus.eq(WritingStatus.NORMAL));

        return PageableExecutionUtils.getPage(listResult, pageable, totalCount::fetchOne);

    }

    @Override
    public Optional<BoardDetailResDto> get(Long boardId) {
        // 조회수 증가
        queryFactory.update(board)
                .set(board.viewCount, board.viewCount.add(1))
                .where(board.id.eq(boardId))
                .execute();

        // likeCount 서브쿼리
        Expression<Long> likeCount = ExpressionUtils.as(JPAExpressions.select(boardLike.countDistinct())
                        .from(boardLike)
                        .where(boardLike.board.id.eq(boardId)),"likeCount");

        //게시글, 작성자, 좋아요수를 가져온다.
        BoardDetailResDto boardResult = queryFactory.select(Projections.constructor(BoardDetailResDto.class,
                        board.id,
                        board.boardScope,
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

        if (boardResult == null) {
            throw new NoSuchElementException();
        }

        //댓글 리스트를 가져온다.
        List<ReplyResDto> replyResDtoList = queryFactory.select(Projections.constructor(ReplyResDto.class,
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

        //모든 대댓글 리스트를 가져온다.
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
                                .where(parentReply.board.id.eq(boardId)))
                        .and(reply.writingStatus.eq(WritingStatus.NORMAL)))
                .orderBy(reply.createdDate.asc())
                .fetch();

        //부모 댓글 번호별로 그룹화
        Map<Long, List<ReplyToReplyResDto>> groupedReReplyMap = reReplyList.stream()
                .collect(Collectors.groupingBy(ReplyToReplyResDto::getParentId));

        replyResDtoList.forEach(replyResDto -> replyResDto.setReplyToReplyList(groupedReReplyMap.get(replyResDto.getId())));

        boardResult.setReplyList(replyResDtoList);
        return Optional.ofNullable(boardResult);
    }

    public Page<BoardResDto> search(BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
        List<BoardResDto> listResult = queryFactory.select(new QBoardResDto(
                        board.id,
                        board.title,
                        board.content,
                        member.nickname,
                        board.viewCount,
                        board.createdDate,
                        boardLike.board.id.countDistinct().as("likeCount"),
                        reply.id.countDistinct().as("replyCount")))
                .from(board)
                .join(board.writer, member)
                .join(boardLike).on(boardLike.board.eq(board))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.writingStatus.eq(WritingStatus.NORMAL),
                        boardTitleLike(boardSearchReqDto.getTitle()),
                        boardWriterLike(boardSearchReqDto.getWriter()))
                .groupBy(board.id)
                .orderBy(board.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory.select(board.id.count())
                .from(board)
                .where(board.writingStatus.eq(WritingStatus.NORMAL),
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

    @Override
    public Optional<Board> findByIdWithWriter(Long boardId) {
        Board findBoard = queryFactory.select(board)
                .from(board)
                .join(board.writer, member).fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();
        return Optional.ofNullable(findBoard);
    }


}
