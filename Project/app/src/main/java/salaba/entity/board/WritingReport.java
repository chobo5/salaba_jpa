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
    @Column(nullable = false)
    private WritingTargetCategory writingTargetCategory;

    @Column(nullable = false)
    private Long writingTargetId;

    @Enumerated(EnumType.STRING)
    private WritingReportCategory writingReportCategory;

    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member reporter;

    public static WritingReport createWritingReport(WritingTargetCategory targetCategory, Long targetId,
                                             WritingReportCategory reportCategory, Member reporter) {
        WritingReport report = new WritingReport();
        report.writingTargetCategory = targetCategory;
        report.writingTargetId = targetId;
        report.writingReportCategory = reportCategory;
        report.processStatus = ProcessStatus.AWAIT;
        report.reporter = reporter;
        reporter.getWritingReportList().add(report);
        return report;
    }

}
