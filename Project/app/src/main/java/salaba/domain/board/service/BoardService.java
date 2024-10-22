package salaba.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.board.dto.request.BoardCreateReqDto;
import salaba.domain.board.dto.request.BoardLikeReqDto;
import salaba.domain.board.dto.request.BoardModifyReqDto;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardByMemberResDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.BoardLike;
import salaba.domain.board.repository.query.BoardQueryRepository;
import salaba.domain.global.constants.WritingStatus;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.board.repository.BoardLikeRepository;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.board.dto.response.BoardModiResDto;
import salaba.domain.member.service.PointService;
import salaba.domain.auth.exception.NoAuthorityException;


import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final PointService pointService;
    private final EntityManager em;
    private final BoardQueryRepository boardQueryRepository;

    public Long create(Long memberId, BoardCreateReqDto boardDto) {
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));
        Board board = Board.create(boardDto.getTitle(), boardDto.getContent(), boardDto.getScope(), writer);

        boardRepository.save(board);

        pointService.createBoardPoint(writer);

        return board.getId();
    }

    public Page<BoardResDto> getPage(Pageable pageable) {
        return boardQueryRepository.getList(pageable);
    }

    public BoardDetailResDto view(Long boardId) {
        boardQueryRepository.increaseViewCount(boardId);

        return boardQueryRepository.findWithRepliesAndLikeCountById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, boardId)));
    }

    public BoardModiResDto modify(Long memberId, BoardModifyReqDto boardDto) {
        Board board = boardQueryRepository.findByIdWithWriter(boardDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, boardDto.getBoardId())));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        if (!board.getWriter().equals(member)) {
            throw new NoAuthorityException("게시물 수정 권한이 없습니다.");
        }

        board.modify(boardDto.getTitle(), boardDto.getContent(), boardDto.getBoardScope());
        //변경된 updatedDate를 응답값에 포함하기 위해 강제로 flush
        em.flush();

        return new BoardModiResDto(board);
    }

    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, boardId)));
        board.delete();
        return board.getId();
    }

    public Page<BoardResDto> search(BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
        return boardQueryRepository.search(boardSearchReqDto, pageable);
    }

    public Long likeBoard(Long memberId, BoardLikeReqDto reqDto) {
        Board board = boardRepository.findById(reqDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, reqDto.getBoardId())));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        BoardLike boardLike = BoardLike.create(board, member);
        boardLikeRepository.save(boardLike);

        return boardLike.getId();
    }

    public Long cancelLikeBoard(Long memberId, BoardLikeReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Board board = boardRepository.findById(reqDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, reqDto.getBoardId())));

        BoardLike boardLike = boardLikeRepository.findByBoardAndMember(board, member)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessage.entityNotFound(BoardLike.class, "boardId: " + reqDto.getBoardId() + " memberId: " + memberId)));

        boardLikeRepository.delete(boardLike);
        return boardLike.getId();
    }

    public Page<BoardByMemberResDto> boardsWrittenByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Page<Board> boardList = boardRepository.findByWriterAndWritingStatus(member, WritingStatus.NORMAL, pageable);
        return boardList.map(board -> new BoardByMemberResDto(board.getId(), board.getTitle(), board.getCreatedDate()));
    }
}
