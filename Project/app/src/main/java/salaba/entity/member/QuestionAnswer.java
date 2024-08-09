package salaba.entity.member;

import lombok.Getter;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class QuestionAnswer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_answer_id")
    private Long id;

    @Column(nullable = false)
    private String answer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public QuestionAnswer(String answer, Question question) {
        this.answer = answer;
        this.question = question;
    }
}
