package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.board.BoardDto;
import salaba.entity.board.BoardCategory;

@SpringBootTest
@Transactional
class BoardRepositoryImplTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void boardListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardDto> list = boardRepository.getList(BoardCategory.FREE, pageRequest);
        list.forEach(System.out::println);

    }

}