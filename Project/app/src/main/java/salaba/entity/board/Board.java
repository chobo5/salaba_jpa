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
@Table(name = "board")
@Getter
@ToString(exclude = {"writer", "boardCategory"})
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "view_count")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    private WritingStatus status;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

}
