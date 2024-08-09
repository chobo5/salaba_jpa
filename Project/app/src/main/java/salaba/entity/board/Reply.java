package salaba.entity.board;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import salaba.entity.BaseEntity;
import salaba.entity.board.Comment;
import salaba.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reply extends BaseEntity {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    private String content;

    @Enumerated(EnumType.STRING)
    private WritingStatus writingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    public static Reply createReply(Comment comment, String content, Member writer) {
        Reply reply = new Reply();
        reply.comment = comment;
        reply.content = content;
        reply.writingStatus = WritingStatus.NORMAL;
        reply.writer = writer;
        writer.getReplyList().add(reply);
        return reply;
    }
}
