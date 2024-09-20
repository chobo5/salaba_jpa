package salaba.domain.reply.entity;

import lombok.*;
import salaba.domain.board.entity.Board;
import salaba.domain.common.constants.WritingStatus;
import salaba.domain.common.entity.BaseEntity;
import salaba.domain.member.entity.Member;

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
    private List<Reply> repliesToReply = new ArrayList<>();

    public static Reply createReply(Board board, String content, Member writer) {
        Reply reply = new Reply();
        reply.board = board;
        reply.content = content;
        reply.writingStatus = WritingStatus.NORMAL;
        reply.writer = writer;
        board.getReplies().add(reply);
        writer.getReplies().add(reply);
        return reply;
    }

    public static Reply createReplyToReply(Reply parent, String content, Member writer) {
        Reply reply = new Reply();
        reply.parent = parent;
        reply.content = content;
        reply.writingStatus = WritingStatus.NORMAL;
        reply.writer = writer;
        parent.getRepliesToReply().add(reply);
        writer.getReplies().add(reply);
        return reply;
    }

    public void deleteReply() {
        board.getReplies().remove(this);
        writingStatus = WritingStatus.DELETED;
    }

    public void deleteReplyToReply() {
        parent.getRepliesToReply().remove(this);
        writingStatus = WritingStatus.DELETED;
    }

    public void modify(String content) {
        this.content = content;
    }
}
