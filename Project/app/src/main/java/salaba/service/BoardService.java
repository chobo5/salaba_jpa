package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.BoardLikeDto;
import salaba.dto.board.BoardCreateDto;
import salaba.dto.board.BoardDto;
import salaba.dto.board.BoardModifyDto;
import salaba.entity.board.Board;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.BoardLike;
import salaba.entity.member.Member;
import salaba.repository.BoardLikeRepository;
import salaba.repository.BoardRepository;
import salaba.repository.MemberRepository;


import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;

    public Long createBoard(BoardCreateDto boardDto) {
        Member writer = memberRepository.findById(boardDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Board board = Board.createBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getCategory(), boardDto.getScope(), writer);
        boardRepository.save(board);
        return board.getId();
    }

    public Page<BoardDto> list(BoardCategory category, Pageable pageable) {
        return boardRepository.getList(category, pageable);
    }

    public Long modifyBoard(BoardModifyDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).orElseThrow(NoSuchElementException::new);

        board.modifyBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getBoardScope());
        return board.getId();
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
