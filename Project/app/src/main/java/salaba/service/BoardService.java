package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.board.BoardCreateDto;
import salaba.dto.board.BoardModifyDto;
import salaba.entity.board.Board;
import salaba.entity.member.Member;
import salaba.repository.BoardRepository;
import salaba.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long createBoard(BoardCreateDto boardDto) {
        Member writer = memberRepository.findById(boardDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Board board = Board.createBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getCategory(), boardDto.getScope(), writer);
        boardRepository.save(board);
        return board.getId();
    }

    public Long modifyBoard(BoardModifyDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).orElseThrow(NoSuchElementException::new);

        board.modifyBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getBoardScope());
        return board.getId();
    }
}
