package salaba.domain.member.entity;

import lombok.Getter;
import salaba.domain.rentalHome.entity.RentalHome;

import javax.persistence.*;

@Entity
@Getter
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    public static BookMark create(Member member, RentalHome rentalHome) {
        BookMark bookMark = new BookMark();
        bookMark.member = member;
        bookMark.rentalHome = rentalHome;
        member.getBookMarks().add(bookMark);
        return bookMark;
    }
}
