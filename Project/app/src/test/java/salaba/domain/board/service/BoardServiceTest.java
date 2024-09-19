package salaba.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.board.dto.request.BoardCreateReqDto;
import salaba.domain.board.entity.BoardLike;
import salaba.domain.board.repository.BoardLikeRepository;
import salaba.domain.board.repository.BoardRepository;
import salaba.domain.member.dto.request.MemberJoinReqDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.AuthService;
import salaba.domain.member.service.PointService;
import salaba.domain.reply.entity.Reply;

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

}