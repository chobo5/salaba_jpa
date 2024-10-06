package salaba.domain.board.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.entity.Board;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.AlarmService;
import salaba.domain.member.service.PointService;
import salaba.domain.board.dto.request.ReplyCreateReqDto;
import salaba.domain.board.dto.request.ReplyModifyReqDto;
import salaba.domain.board.dto.request.ReplyToReplyCreateReqDto;
import salaba.domain.board.entity.Reply;
import salaba.domain.board.repository.ReplyRepository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {
    @InjectMocks
    private ReplyService replyService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private PointService pointService;

    @Mock
    private EntityManager em;

    @Mock
    private AlarmService alarmService;

    @Test
    public void 댓글생성() {
        //given
        Long memberId = 1L;
        ReplyCreateReqDto reqDto = new ReplyCreateReqDto(1L, "content");

        //when
        Member member = Member.create("test@test.com", "Aa123456@", "name", "test_nickname", LocalDate.of(1996, 10, 8));
        Board board = Board.create("testTitle", "test content", BoardScope.ALL, member);
        Reply reply = Reply.createReply(board, reqDto.getContent(), member);

        when(boardRepository.findByIdWithWriter(reqDto.getBoardId())).thenReturn(Optional.of(board));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        doNothing().when(alarmService).createReplyAlarm(board.getWriter(), member, reply.getContent());
        doNothing().when(pointService).createReplyPoint(member);

        replyService.create(memberId, reqDto);
        //then
        verify(boardRepository, times(1)).findByIdWithWriter(reqDto.getBoardId());
        verify(memberRepository, times(1)).findById(memberId);
        verify(replyRepository, times(1)).save(any(Reply.class));
        verify(alarmService).createReplyAlarm(board.getWriter(), member, reply.getContent());
        verify(pointService).createReplyPoint(member);
    }

    @Test
    public void 대댓글생성() {
        //given
        Long memberId = 1L;
        ReplyToReplyCreateReqDto reqDto = new ReplyToReplyCreateReqDto(1L, "content");

        //when
        Member member = Member.create("test@test.com", "Aa123456@", "name", "test_nickname", LocalDate.of(1996, 10, 8));
        Board board = Board.create("testTitle", "test content", BoardScope.ALL, member);
        Reply reply = Reply.createReply(board, "content", member);
        Reply reReply = Reply.createReplyToReply(reply, reqDto.getContent(), member);

        when(replyRepository.findByIdWithWriter(reqDto.getReplyId())).thenReturn(Optional.of(reply));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        doNothing().when(alarmService).createReplyAlarm(board.getWriter(), member, reply.getContent());

        replyService.createToReply(memberId, reqDto);
        //then
        verify(replyRepository, times(1)).findByIdWithWriter(reqDto.getReplyId());
        verify(replyRepository, times(1)).save(any(Reply.class));
        verify(memberRepository, times(1)).findById(memberId);
        verify(alarmService).createReplyAlarm(board.getWriter(), member, reply.getContent());
    }


    @Test
    public void 댓글수정() {
        //given
        Long memberId = 1L;
        ReplyModifyReqDto reqDto = new ReplyModifyReqDto(1L, "modified content");

        //when
        Member member = Member.create("test@test.com", "Aa123456@", "name", "test_nickname", LocalDate.of(1996, 10, 8));
        Board board = Board.create("testTitle", "test content", BoardScope.ALL, member);
        Reply reply = Reply.createReply(board, reqDto.getContent(), member);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(replyRepository.findByIdWithWriter(reqDto.getReplyId())).thenReturn(Optional.of(reply));
        reply.modify(reqDto.getContent());

        replyService.modify(reqDto, memberId);
        //then
        verify(replyRepository, times(1)).findByIdWithWriter(reqDto.getReplyId());
        verify(memberRepository, times(1)).findById(memberId);

    }

    @Test
    void 댓글삭제() {
        //given
        Long replyId = 1L;
        Long memberId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456@", "name", "test_nickname", LocalDate.of(1996, 10, 8));
        Board board = Board.create("testTitle", "test content", BoardScope.ALL, member);
        Reply reply = Reply.createReply(board, "content", member);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(replyRepository.findByIdWithWriter(replyId)).thenReturn(Optional.of(reply));
        reply.delete();

        replyService.delete(replyId, memberId);
        //then
        verify(replyRepository, times(1)).findByIdWithWriter(replyId);
        verify(memberRepository, times(1)).findById(memberId);

    }


    @Test
    void 회원이쓴댓글목록() {
        //given
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member member = Member.create("test@test.com", "Aa123456@", "name", "test_nickname", LocalDate.of(1996, 10, 8));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Board board = Board.create("testTitle", "test content", BoardScope.ALL, member);
        List<Reply> replies = Arrays.asList(Reply.createReply(board, "content", member), Reply.createReply(board, "content2", member));
        when(replyRepository.findByWriter(member, pageable)).thenReturn(new PageImpl<>(replies, pageable, 2L));

        replyService.getRepliesWrittenByMember(memberId, pageable);
        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(replyRepository, times(1)).findByWriter(member, pageable);
    }
}