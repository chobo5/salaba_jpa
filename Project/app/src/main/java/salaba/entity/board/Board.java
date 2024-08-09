package salaba.entity.board;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import salaba.entity.BaseEntity;
import salaba.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Lob
    private String content;

    @Column(name = "view_count")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    private WritingStatus writingStatus;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikeList = new ArrayList<>();

    public static Board createBoard(String title, String content, BoardCategory boardCategory, Member writer) {
        Board newBoard = new Board();
        newBoard.title = title;
        newBoard.content = content;
        newBoard.viewCount = 0;
        newBoard.writingStatus = WritingStatus.NORMAL;
        newBoard.boardCategory = boardCategory;
        newBoard.writer = writer;
        writer.getBoardList().add(newBoard);
        return newBoard;
    }


}
