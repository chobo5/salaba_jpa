package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.response.ReplyByMemberResDto;
import salaba.dto.request.board.ReplyCreateReqDto;
import salaba.dto.request.board.ReplyModifyReqDto;
import salaba.dto.request.board.ReplyToReplyCreateReqDto;
import salaba.entity.board.Board;
import salaba.entity.board.Reply;
import salaba.entity.member.Alarm;
import salaba.entity.member.Member;
import salaba.entity.member.Point;
import salaba.repository.AlarmRepository;
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
    private final AlarmRepository alarmRepository;

    public Long createReply(ReplyCreateReqDto replyCreateReqDto) {
        Board board = boardRepository.findByIdWithWriter(replyCreateReqDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(replyCreateReqDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Reply reply = Reply.createReply(board, replyCreateReqDto.getContent(), member);
        replyRepository.save(reply);

        Alarm alarm = Alarm.createReplyAlarm(board.getWriter(), member.getNickname(), reply.getContent());
        alarmRepository.save(alarm);

        pointRepository.save(Point.createReplyPoint(member));

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

    public Long createReplyToReply(ReplyToReplyCreateReqDto replyToReplyCreateReqDto) {
        Reply parent = replyRepository.findByIdWithWriter(replyToReplyCreateReqDto.getReplyId()).orElseThrow(NoSuchElementException::new);
        Member writer = memberRepository.findById(replyToReplyCreateReqDto.getMemberId()).orElseThrow(NoSuchElementException::new);

        Reply reply = Reply.createReplyToReply(parent, replyToReplyCreateReqDto.getContent(), writer);
        replyRepository.save(reply);

        Alarm alarm = Alarm.createReplyAlarm(parent.getWriter(), writer.getNickname(), reply.getContent());
        alarmRepository.save(alarm);
        return reply.getId();
    }

    public Long deleteReplyToReply(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        reply.deleteReplyToReply();
        return reply.getId();
    }

    public Page<ReplyByMemberResDto> repliesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Reply> replyList = replyRepository.findByWriter(member, pageable);
        return replyList.map(reply -> new ReplyByMemberResDto(reply.getId(), reply.getContent(), reply.getCreatedDate()));
    }
}
