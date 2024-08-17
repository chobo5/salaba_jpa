package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.BoardSearchDto;
import salaba.dto.board.*;
import salaba.entity.board.Board;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.BoardLike;
import salaba.entity.member.Member;
import salaba.repository.BoardLikeRepository;
import salaba.repository.BoardRepository;
import salaba.repository.MemberRepository;
import salaba.response.BoardModifiedResponse;


import javax.persistence.EntityManager;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final EntityManager em;

    public Long createBoard(BoardCreateDto boardDto) {
        Member writer = memberRepository.findById(boardDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Board board = Board.createBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getCategory(), boardDto.getScope(), writer);
        boardRepository.save(board);
        return board.getId();
    }

    public Page<BoardDto> list(BoardCategory category, Pageable pageable) {
        return boardRepository.getList(category, pageable);
    }

    public BoardDetailDto get(Long boardId) {
        return boardRepository.get(boardId);
    }

    public BoardModifiedResponse modify(BoardModifyDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).orElseThrow(NoSuchElementException::new);

        board.modifyBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getBoardScope());
        em.flush(); //변경된 updatedDate를 응답값에 포함하기 위해 강제로 flush(요청을 날려도 entity가 변경되지 않으면 업데이트 되지 않음)
        return new BoardModifiedResponse(board.getId(), board.getTitle(), board.getContent(), board.getBoardScope(), board.getCreatedDate(), board.getUpdatedDate());
    }

    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
        board.deleteBoard();
        return board.getId();
    }

    public Page<BoardDto> search(BoardCategory category, BoardSearchDto boardSearchDto, Pageable pageable) {
        return boardRepository.search(category, boardSearchDto, pageable);
    }

    public Long likeBoard(BoardLikeDto boardLikeDto) {
        Board board = boardRepository.findById(boardLikeDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(boardLikeDto.getMemberId()).orElseThrow(NoSuchElementException::new);

        BoardLike boardLike = BoardLike.createBoardLike(board, member);
        boardLikeRepository.save(boardLike);

        return boardLike.getId();
    }

    public Long cancelLikeBoard(BoardLikeDto boardLikeDto) {
        BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardLikeDto.getBoardId(), boardLikeDto.getMemberId())
                .orElseThrow(NoSuchElementException::new);

        boardLike.cancelBoardLike();
        boardLikeRepository.delete(boardLike);
        return boardLike.getId();
    }
}
