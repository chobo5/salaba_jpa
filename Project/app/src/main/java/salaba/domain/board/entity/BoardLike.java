package salaba.domain.board.entity;

import lombok.*;
import salaba.domain.member.entity.Member;

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
        board.getBoardLikes().add(boardLike);
        return boardLike;
    }

    public void cancelBoardLike() {
        board.getBoardLikes().remove(this);
    }

}
