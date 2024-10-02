package salaba.domain.board.entity;

import lombok.*;
import salaba.global.entity.BaseEntity;
import salaba.domain.board.constants.BoardScope;
import salaba.global.constants.WritingStatus;
import salaba.domain.member.entity.Member;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<>();


    public static Board create(String title, String content, BoardScope boardScope, Member writer) {
        Board newBoard = new Board();
        newBoard.title = title;
        newBoard.content = content;
        newBoard.viewCount = 0;
        newBoard.writingStatus = WritingStatus.NORMAL;
        newBoard.boardScope = boardScope;
        newBoard.writer = writer;
        writer.getBoards().add(newBoard);
        return newBoard;
    }

    public void delete() {
        writingStatus = WritingStatus.DELETED;
    }

    public void modify(String title, String content, BoardScope boardScope) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
        this.boardScope = boardScope != null ? boardScope : this.boardScope;
    }


}


