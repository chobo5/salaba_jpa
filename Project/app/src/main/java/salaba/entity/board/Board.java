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
    private BoardScope boardScope;

    @Enumerated(EnumType.STRING)
    private WritingStatus writingStatus;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "board")
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardFile> boardFileList = new ArrayList<>();

    public static Board createBoard(String title, String content, BoardCategory boardCategory, BoardScope boardScope, Member writer) {
        Board newBoard = new Board();
        newBoard.title = title;
        newBoard.content = content;
        newBoard.viewCount = 0;
        newBoard.writingStatus = WritingStatus.NORMAL;
        newBoard.boardCategory = boardCategory;
        newBoard.boardScope = boardScope;
        newBoard.writer = writer;
        writer.getBoardList().add(newBoard);
        return newBoard;
    }

    public void deleteBoard() {
        writingStatus = WritingStatus.DELETED;
    }

    public void viewBoard() {
        viewCount++;
    }

    public void modifyBoard(String title, String content, BoardScope boardScope) {
        this.title = title;
        this.content = content;
        this.boardScope = boardScope;
    }


}


