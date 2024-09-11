package salaba.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.service.AlarmService;
import salaba.domain.member.service.PointService;
import salaba.domain.reply.dto.response.ReplyByMemberResDto;
import salaba.domain.reply.dto.request.ReplyCreateReqDto;
import salaba.domain.reply.dto.request.ReplyModifyReqDto;
import salaba.domain.reply.dto.request.ReplyToReplyCreateReqDto;
import salaba.domain.board.entity.Board;
import salaba.domain.reply.entity.Reply;
import salaba.domain.member.entity.Member;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.reply.repository.ReplyRepository;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.reply.dto.response.ReplyModiResDto;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final PointService pointService;
    private final EntityManager em;
    private final AlarmService alarmService;

    public Long createReply(Long memberId, ReplyCreateReqDto replyCreateReqDto) {
        Board board = boardRepository.findByIdWithWriter(replyCreateReqDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Reply reply = Reply.createReply(board, replyCreateReqDto.getContent(), member);
        replyRepository.save(reply);

        alarmService.createReplyAlarm(board.getWriter(), member, reply.getContent());
        pointService.createReplyPoint(member);

        return reply.getId();
    }

    public ReplyModiResDto modify(ReplyModifyReqDto replyModifyReqDto) {
        Reply reply = replyRepository.findById(replyModifyReqDto.getReplyId()).orElseThrow(NoSuchElementException::new);
        reply.modifyReply(replyModifyReqDto.getContent());
        em.flush();
        return new ReplyModiResDto(reply.getId(), reply.getContent(), reply.getCreatedDate(), reply.getUpdatedDate());
    }

    public Long delete(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        reply.deleteReply();
        return reply.getId();
    }

    public Long createReplyToReply(Long memberId, ReplyToReplyCreateReqDto replyToReplyCreateReqDto) {
        Reply parent = replyRepository.findByIdWithWriter(replyToReplyCreateReqDto.getReplyId()).orElseThrow(NoSuchElementException::new);
        Member writer = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);

        Reply reply = Reply.createReplyToReply(parent, replyToReplyCreateReqDto.getContent(), writer);
        replyRepository.save(reply);

        alarmService.createReplyAlarm(parent.getWriter(), writer, reply.getContent());

        return reply.getId();
    }

    public Long deleteReplyToReply(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        reply.deleteReplyToReply();
        return reply.getId();
    }

    public Page<ReplyByMemberResDto> getRepliesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Reply> replyList = replyRepository.findByWriter(member, pageable);
        return replyList.map(reply -> new ReplyByMemberResDto(reply.getId(), reply.getContent(), reply.getCreatedDate()));
    }
}
