package salaba.entity.board;

import lombok.*;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "board_file")
@Getter
public class BoardFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "ori_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "uuid_file_name", unique = true, nullable = false)
    private String uuidFileName;
}
