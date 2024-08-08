package salaba.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import salaba.dto.PageRequestDto;
import salaba.dto.PageResultDto;
import salaba.dto.board.BoardDetailDto;
import salaba.dto.board.BoardDto;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDetailDto boardDetailDto = BoardDetailDto
                .builder()
                .headNo(1)
                .categoryNo(1)
                .title("springboot test")
                .content("springboot test")
                .writerNo(13L)
                .build();
        System.out.println("regist result is " + boardService.register(boardDetailDto));
    }

    @Test
    public void testListEntityToDto() {
        PageRequestDto pageRequestDto = new PageRequestDto();

        PageResultDto<BoardDto, Object[]> result = boardService.getBoardList(pageRequestDto, 1);

        for (BoardDto boardDto : result.getDtoList()) {
            System.out.println(boardDto);
        }
    }
}
