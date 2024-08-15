package salaba.entity.board;

import lombok.*;
import salaba.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static BoardLike createBoardLike(Board board, Member member) {
        BoardLike boardLike = new BoardLike();
        boardLike.member = member;
        boardLike.board = board;
        board.getBoardLikeList().add(boardLike);
        return boardLike;
    }

    public void cancelBoardLike() {
        board.getBoardLikeList().remove(this);
    }

}
