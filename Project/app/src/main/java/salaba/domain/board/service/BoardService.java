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
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.domain.member.repository.PointRepository;
import salaba.domain.board.repository.BoardLikeRepository;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.board.dto.response.BoardModiResDto;
import salaba.domain.member.service.PointService;


import javax.persistence.EntityManager;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final PointService pointService;
    private final EntityManager em;

    public Long createBoard(Long memberId, BoardCreateReqDto boardDto) {
        Member writer = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Board board = Board.createBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getScope(), writer);

        boardRepository.save(board);

        pointService.createBoardPoint(writer);

        return board.getId();
    }

    public Page<BoardResDto> list(Pageable pageable) {
        return boardRepository.getList(pageable);
    }

    public BoardDetailResDto get(Long boardId) {

        return boardRepository.get(boardId).orElseThrow(NoSuchElementException::new);
    }

    public BoardModiResDto modify(BoardModifyReqDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).orElseThrow(NoSuchElementException::new);

        board.modifyBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getBoardScope());
        em.flush(); //변경된 updatedDate를 응답값에 포함하기 위해 강제로 flush(요청을 날려도 entity가 변경되지 않으면 업데이트 되지 않음)
        return new BoardModiResDto(board.getId(), board.getTitle(), board.getContent(), board.getBoardScope(), board.getCreatedDate(), board.getUpdatedDate());
    }

    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
        board.deleteBoard();
        return board.getId();
    }

    public Page<BoardResDto> search(BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
        return boardRepository.search(boardSearchReqDto, pageable);
    }

    public Long likeBoard(Long memberId, BoardLikeReqDto boardLikeReqDto) {
        Board board = boardRepository.findById(boardLikeReqDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);

        BoardLike boardLike = BoardLike.createBoardLike(board, member);
        boardLikeRepository.save(boardLike);

        return boardLike.getId();
    }

    public Long cancelLikeBoard(Long memberId, BoardLikeReqDto boardLikeReqDto) {
        BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardLikeReqDto.getBoardId(), memberId)
                .orElseThrow(NoSuchElementException::new);

        boardLike.cancelBoardLike();
        boardLikeRepository.delete(boardLike);
        return boardLike.getId();
    }

    public Page<BoardByMemberResDto> boardsByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Board> boardList = boardRepository.findByWriter(member, pageable);
        return boardList.map(board -> new BoardByMemberResDto(board.getId(), board.getTitle(), board.getCreatedDate()));
    }
}
