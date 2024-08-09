package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.BaseEntity;
import salaba.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomeReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentalReportCategory rentalReportCategory;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static RentalHomeReport createRentalHomeReport (RentalReportCategory rentalReportCategory, String content, RentalHome rentalHome, Member member) {
        RentalHomeReport rentalHomeReport = new RentalHomeReport();
        rentalHomeReport.rentalReportCategory = rentalReportCategory;
        rentalHomeReport.content = content;
        rentalHomeReport.rentalHome = rentalHome;
        rentalHomeReport.member = member;
        rentalHome.getRentalHomeReportList().add(rentalHomeReport);
        return rentalHomeReport;
    }
}
