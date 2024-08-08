package salaba.entity.board;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import salaba.entity.BaseEntity;
import salaba.entity.board.Comment;
import salaba.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
public class Reply extends BaseEntity {
    @Id
    @Column(name = "reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no", nullable = false)
    private Comment comment;

    @Lob
    private String content;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private WritingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member writer;


}
