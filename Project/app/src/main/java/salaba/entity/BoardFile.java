package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long boardFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no", nullable = false)
    private Board board;

    @Column(name = "ori_file_name")
    private String originalFileName;

    @Column(name = "uuid_file_name", unique = true)
    private String uuidFileName;
}
