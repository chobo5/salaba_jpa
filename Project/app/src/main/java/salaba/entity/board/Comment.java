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



}
