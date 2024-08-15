package salaba.entity.board;

import lombok.*;
import salaba.entity.BaseEntity;
import salaba.entity.member.Member;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Reply extends BaseEntity {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    @Enumerated(EnumType.STRING)
    private WritingStatus writingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent")
    private List<Reply> replyToReplyList = new ArrayList<>();

    public static Reply createReply(Board board, String content, Member writer) {
        Reply reply = new Reply();
        reply.board = board;
        reply.content = content;
        reply.writingStatus = WritingStatus.NORMAL;
        reply.writer = writer;
        board.getReplyList().add(reply);
        writer.getReplyList().add(reply);
        return reply;
    }

    public static Reply createReplyToReply(Reply parent, String content, Member writer) {
        Reply reply = new Reply();
        reply.parent = parent;
        reply.content = content;
        reply.writingStatus = WritingStatus.NORMAL;
        reply.writer = writer;
        parent.getReplyToReplyList().add(reply);
        writer.getReplyList().add(reply);
        return reply;
    }

    public void deleteReply() {
        board.getReplyList().remove(this);
        writingStatus = WritingStatus.DELETED;
    }

    public void deleteReplyToReply() {
        parent.getReplyToReplyList().remove(this);
        writingStatus = WritingStatus.DELETED;
    }

    public void modifyReply(String content) {
        this.content = content;
    }
}
