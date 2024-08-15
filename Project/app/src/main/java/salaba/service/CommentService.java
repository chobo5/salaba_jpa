package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.board.CommentCreateDto;
import salaba.entity.board.Board;
import salaba.entity.board.Comment;
import salaba.entity.member.Member;
import salaba.repository.BoardRepository;
import salaba.repository.CommentRepository;
import salaba.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public Long createComment(CommentCreateDto commentCreateDto) {
        Board board = boardRepository.findById(commentCreateDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(commentCreateDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Comment comment = Comment.createComment(board, commentCreateDto.getContent(), member);

        commentRepository.save(comment);

        return comment.getId();
    }
}
