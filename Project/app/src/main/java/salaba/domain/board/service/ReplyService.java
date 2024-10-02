package salaba.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.service.AlarmService;
import salaba.domain.member.service.PointService;
import salaba.domain.board.dto.response.ReplyByMemberResDto;
import salaba.domain.board.dto.request.ReplyCreateReqDto;
import salaba.domain.board.dto.request.ReplyModifyReqDto;
import salaba.domain.board.dto.request.ReplyToReplyCreateReqDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.Reply;
import salaba.domain.member.entity.Member;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.board.repository.ReplyRepository;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.board.dto.response.ReplyModiResDto;
import salaba.domain.auth.exception.CannotFindMemberException;
import salaba.domain.auth.exception.NoAuthorityException;

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

    public ReplyModiResDto modify(ReplyModifyReqDto replyModifyReqDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(CannotFindMemberException::new);
        Reply reply = replyRepository.findByIdWithWriter(replyModifyReqDto.getReplyId()).orElseThrow(NoSuchElementException::new);

        if(!reply.getWriter().equals(member)) {
            throw new NoAuthorityException("댓글 수정 권한이 없습니다.");
        }

        reply.modify(replyModifyReqDto.getContent());
        em.flush();
        return new ReplyModiResDto(reply.getId(), reply.getContent(), reply.getCreatedDate(), reply.getUpdatedDate());
    }

    public Long delete(Long replyId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(CannotFindMemberException::new);
        Reply reply = replyRepository.findByIdWithWriter(replyId).orElseThrow(NoSuchElementException::new);

        if(!reply.getWriter().equals(member)) {
            throw new NoAuthorityException("댓글 삭제 권한이 없습니다.");
        }

        reply.delete();
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


    public Page<ReplyByMemberResDto> getRepliesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Page<Reply> replyList = replyRepository.findByWriter(member, pageable);
        return replyList.map(reply -> new ReplyByMemberResDto(reply.getId(), reply.getContent(), reply.getCreatedDate()));
    }
}
