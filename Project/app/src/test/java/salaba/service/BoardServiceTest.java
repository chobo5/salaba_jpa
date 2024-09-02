package salaba.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.request.MemberJoinReqDto;
import salaba.entity.board.Board;
import salaba.entity.board.BoardScope;
import salaba.entity.member.Member;
import salaba.repository.MemberRepository;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void boardCreateTest() {
        MemberJoinReqDto dto = new MemberJoinReqDto("test11", "test11", "test11@test.com", "Tt12241509!@", LocalDate.of(2021, 10, 11));
        Long memberId = authService.join(dto);
        Member member = memberRepository.findById(memberId).get();
        Board board = Board.createBoard("test", "test", BoardScope.ALL, member);
    }
}