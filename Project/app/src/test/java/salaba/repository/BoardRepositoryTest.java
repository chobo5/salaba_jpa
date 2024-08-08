package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.board.Board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        StringBuilder sb = new StringBuilder();
        result.get().forEach(board -> sb.append(board).append("\n"));
        System.out.println("============================================");
        System.out.println(sb);

    }

    @Test
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(10L);
        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void getBoard() {
        List<Object[]> result = boardRepository.getBoard(11L);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void getBoardList() {
        Pageable pageable = PageRequest.of(0, 10 , Sort.by("createdDate").descending());
        Page<Object[]> result = boardRepository.getBoardList(1, pageable);
        result.get().forEach(objects -> {
            Object[] arr = (Object[]) objects;
            System.out.println(Arrays.toString(arr));
        });

    }


//    @Test
//    public void getBoardAndCommentsAndReplies() {
//        List<Object[]> board = boardRepository.getBoardAndCommentsAndReplies(11L);
//        for (Object[] arr : board) {
//            System.out.println(Arrays.toString(arr));
//        }
//    }

    @Test
    public void testSearch1() {
         boardRepository.search1(1);
    }

    @Test
    public void testSearchBoard() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").descending());
        Page<Object[]> result = boardRepository.searchBoard("t", "안녕", pageable);
        for (Object[] objects : result) {
            System.out.println(objects);
        }
    }

}
