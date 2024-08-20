package salaba.entity.board;

import lombok.*;
import salaba.entity.FileBaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class BoardFile extends FileBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public static BoardFile createBoardFile(Board board, String filename) {
        BoardFile boardFile = new BoardFile();
        boardFile.board = board;
        boardFile.setFiles(filename);
        board.getBoardFiles().add(boardFile);
        return boardFile;
    }

    public void deleteBoardFile() {
        board.getBoardFiles().remove(this);
    }
}
