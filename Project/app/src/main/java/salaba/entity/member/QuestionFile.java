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

    public static QuestionFile crateQuestionFile(String filename, Question question) {
        QuestionFile questionFile = new QuestionFile();
        questionFile.setFiles(filename);
        questionFile.question = question;
        question.getQuestionFileList().add(questionFile);
        return questionFile;
    }
}
