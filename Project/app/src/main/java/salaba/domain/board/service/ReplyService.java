package salaba.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.exception.ErrorMessage;
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
import salaba.domain.auth.exception.NoAuthorityException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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

    public Long createReply(Long memberId, ReplyCreateReqDto reqDto) {
        Board board = boardRepository.findByIdWithWriter(reqDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Board.class, reqDto.getBoardId())));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reply reply = Reply.createReply(board, reqDto.getContent(), member);
        replyRepository.save(reply);

        alarmService.createReplyAlarm(board.getWriter(), member, reply.getContent());
        pointService.createReplyPoint(member);

        return reply.getId();
    }

    public ReplyModiResDto modify(ReplyModifyReqDto reqDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));
        Reply reply = replyRepository.findByIdWithWriter(reqDto.getReplyId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reply.class, reqDto.getReplyId())));

        if(!reply.getWriter().equals(member)) {
            throw new NoAuthorityException("댓글 수정 권한이 없습니다.");
        }

        reply.modify(reqDto.getContent());
        em.flush();
        return new ReplyModiResDto(reply.getId(), reply.getContent(), reply.getCreatedDate(), reply.getUpdatedDate());
    }

    public Long delete(Long replyId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reply reply = replyRepository.findByIdWithWriter(replyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reply.class, replyId)));

        if(!reply.getWriter().equals(member)) {
            throw new NoAuthorityException("댓글 삭제 권한이 없습니다.");
        }

        reply.delete();
        return reply.getId();
    }

    public Long createReplyToReply(Long memberId, ReplyToReplyCreateReqDto reqDto) {
        Reply parent = replyRepository.findByIdWithWriter(reqDto.getReplyId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Reply.class, reqDto.getReplyId())));
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Reply reply = Reply.createReplyToReply(parent, reqDto.getContent(), writer);
        replyRepository.save(reply);

        alarmService.createReplyAlarm(parent.getWriter(), writer, reply.getContent());

        return reply.getId();
    }


    public Page<ReplyByMemberResDto> getRepliesByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Page<Reply> replyList = replyRepository.findByWriter(member, pageable);
        return replyList.map(reply -> new ReplyByMemberResDto(reply.getId(), reply.getContent(), reply.getCreatedDate()));
    }
}
