package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.Board;
import salaba.entity.Comment;
import salaba.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Test
    public void addComment() {
        Board board = Board.builder().boardNo(11L).build();
        Member member = Member.builder().memberNo(8L).build();

        List<Comment> commentList = new ArrayList<>();

        IntStream.rangeClosed(1, 4).forEach(i -> {
            Comment comment = Comment.builder()
                    .board(board)
                    .content("댓글 " + i)
//                    .writer(member)
                    .build();
            commentList.add(comment);
        });

        commentRepository.saveAll(commentList);
    }

    @Test
    @Transactional
    public void getComment() {
        Optional<Comment> result = commentRepository.findById(1L);
        Comment comment = result.get();
        System.out.println(comment);
    }
}
