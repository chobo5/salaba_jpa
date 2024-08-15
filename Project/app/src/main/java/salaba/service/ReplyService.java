package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.board.ReplyCreateDto;
import salaba.entity.board.Board;
import salaba.entity.board.Reply;
import salaba.entity.member.Member;
import salaba.repository.BoardRepository;
import salaba.repository.ReplyRepository;
import salaba.repository.MemberRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;

    public Long createReply(ReplyCreateDto replyCreateDto) {
        Board board = boardRepository.findById(replyCreateDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(replyCreateDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Reply reply = Reply.createReply(board, replyCreateDto.getContent(), member);

        replyRepository.save(reply);

        return reply.getId();
    }
}
