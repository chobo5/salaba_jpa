package salaba.entity.board;

import lombok.Getter;
import salaba.entity.BaseEntity;
import salaba.entity.ProcessStatus;
import salaba.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
public class WritingReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WritingTargetCategory writingTargetCategory;

    private Long writingTargetId;

    @Enumerated(EnumType.STRING)
    private WritingReportCategory writingReportCategory;

    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member reporter;

}
