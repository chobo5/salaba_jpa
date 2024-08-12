package salaba.entity.board;

import lombok.*;
import salaba.entity.BaseEntity;
import salaba.entity.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replyList = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private WritingStatus writingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    public static Comment createComment(Board board, String content, Member writer) {
        Comment comment = new Comment();
        comment.board = board;
        comment.content = content;
        comment.writingStatus = WritingStatus.NORMAL;
        comment.writer = writer;
        writer.getCommentList().add(comment);
        return comment;
    }

    public void deleteComment() {
        writingStatus = WritingStatus.DELETED;
//        writer.getCommentList().remove(this); //삭제된 댓글입니다로 표시
    }

    public void modifyComment(String content) {
        this.content = content;
    }

}
