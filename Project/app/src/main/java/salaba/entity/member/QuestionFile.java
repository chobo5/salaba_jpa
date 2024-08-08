package salaba.entity.member;

import lombok.Getter;
import salaba.entity.BaseEntity;
import salaba.entity.FileBaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class QuestionFile extends FileBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_file")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
