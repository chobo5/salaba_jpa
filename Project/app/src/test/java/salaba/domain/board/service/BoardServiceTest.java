package salaba.domain.board.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import salaba.domain.board.dto.request.BoardCreateReqDto;
import salaba.domain.board.dto.request.BoardModifyReqDto;
import salaba.domain.board.repository.BoardLikeRepository;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.board.entity.Board;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.PointService;
import salaba.domain.auth.exception.NoAuthorityException;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BoardRepository boardRepository;
    
    @Mock
    private BoardLikeRepository boardLikeRepository;
    
    @Mock
    private PointService pointService;
    
    @Mock
    private EntityManager em;
    
    
    @Test
    public void 게시글작성() {
        //given
        Long memberId = 1L;
        BoardCreateReqDto reqDto = new BoardCreateReqDto("title", "content", BoardScope.ALL);
        
        //when
        Member member = Member.create("test1@test.com", "Aa1234567!@", "testname1",
                "testNickName1", LocalDate.of(1996, 10, 8));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        doNothing().when(pointService).createBoardPoint(member);

        boardService.createBoard(memberId, reqDto);

        //then
        verify(boardRepository, times(1)).save(any(Board.class));
        verify(pointService, times(1)).createBoardPoint(member);

    }

    @Test
    public void 게시글수정() {
        //given
        Long memberId = 1L;
        BoardModifyReqDto reqDto = new BoardModifyReqDto(1L, "modifiedTitle", "modifiedContent", null);

        //when
        Member member = Member.create("test@test.com", "password", "name",
                "nickname", LocalDate.of(1996, 10, 8));
        Board board = Board.create("title", "content", BoardScope.ALL, member);

        when(boardRepository.findByIdWithWriter(reqDto.getBoardId())).thenReturn(Optional.of(board));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        boardService.modify(memberId, reqDto);

        //then
        verify(boardRepository, times(1)).findByIdWithWriter(reqDto.getBoardId());
        verify(memberRepository, times(1)).findById(memberId);

    }

    @Test
    public void 게시글수정실패_권한없음() {
        //given
        Long memberId = 1L;
        BoardModifyReqDto reqDto = new BoardModifyReqDto(1L, "modifiedTitle", "modifiedContent", null);

        //when
        Member member = Member.create("test@test.com", "password", "name",
                "nickname", LocalDate.of(1996, 10, 8));

        Member otherMember = Member.create("test2@test.com", "password2", "name2",
                "nickname2", LocalDate.of(1996, 10, 8));
        Board board = Board.create("title", "content", BoardScope.ALL, member);

        when(boardRepository.findByIdWithWriter(reqDto.getBoardId())).thenReturn(Optional.of(board));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(otherMember));

        //then
        Assertions.assertThrows(NoAuthorityException.class, () -> boardService.modify(memberId, reqDto));
        verify(boardRepository, times(1)).findByIdWithWriter(reqDto.getBoardId());
        verify(memberRepository, times(1)).findById(memberId);

    }


}