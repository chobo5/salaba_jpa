package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.board.ReplyByMemberDto;
import salaba.dto.request.board.ReplyCreateDto;
import salaba.dto.request.board.ReplyModifyDto;
import salaba.dto.request.board.ReplyToReplyCreateDto;
import salaba.entity.board.Board;
import salaba.entity.board.Reply;
import salaba.entity.member.Member;
import salaba.entity.member.Point;
import salaba.repository.PointRepository;
import salaba.repository.board.BoardRepository;
import salaba.repository.board.ReplyRepository;
import salaba.repository.MemberRepository;
import salaba.dto.response.ReplyModiResDto;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final PointRepository pointRepository;
    private final EntityManager em;

    public Long createReply(ReplyCreateDto replyCreateDto) {
        Board board = boardRepository.findById(replyCreateDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(replyCreateDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Reply reply = Reply.createReply(board, replyCreateDto.getContent(), member);

        replyRepository.save(reply);

        pointRepository.save(Point.createReplyPoint(member));

        return reply.getId();
    }

    public ReplyModiResDto modify(ReplyModifyDto replyModifyDto) {
        Reply reply = replyRepository.findById(replyModifyDto.getId()).orElseThrow(NoSuchElementException::new);
        reply.modifyReply(replyModifyDto.getContent());
        em.flush();
        return new ReplyModiResDto(reply.getId(), reply.getContent(), reply.getCreatedDate(), reply.getUpdatedDate());
    }

    public Long delete(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        reply.deleteReply();
        return reply.getId();
    }

    public Long createReplyToReply(ReplyToReplyCreateDto replyToReplyCreateDto) {
        Reply parent = replyRepository.findById(replyToReplyCreateDto.getReplyId()).orElseThrow(NoSuchElementException::new);
        Member writer = memberRepository.findById(replyToReplyCreateDto.getMemberId()).orElseThrow(NoSuchElementException::new);

        Reply reply = Reply.createReplyToReply(parent, replyToReplyCreateDto.getContent(), writer);
        replyRepository.save(reply);
        return reply.getId();
    }

    public Long deleteReplyToReply(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        reply.deleteReplyToReply();
        return reply.getId();
    }

    public Page<ReplyByMemberDto> repliesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Reply> replyList = replyRepository.findByWriter(member, pageable);
        return replyList.map(reply -> new ReplyByMemberDto(reply.getId(), reply.getContent(), reply.getCreatedDate()));
    }
}
