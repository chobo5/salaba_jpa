package salaba.domain.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.dto.request.MemberJoinReqDto;
import salaba.domain.board.entity.Board;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.AuthService;

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