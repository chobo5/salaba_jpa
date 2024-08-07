package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_no")
    private Long boardLikeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;


}
