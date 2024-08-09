package salaba.entity.member;

import lombok.Getter;
import salaba.entity.BaseEntity;
import salaba.entity.ProcessStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<QuestionFile> questionFileList = new ArrayList<>();

    public Question createQuestion(String title, String content, Member member) {
        Question question = new Question();
        question.title = title;
        question.content = content;
        question.status = ProcessStatus.AWAIT;
        question.member = member;
        member.getQuestionList().add(question);
        return question;
    }
}
