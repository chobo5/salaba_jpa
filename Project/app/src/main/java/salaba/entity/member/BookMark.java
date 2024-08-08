package salaba.entity.member;

import lombok.Getter;
import salaba.entity.rental.RentalHome;

import javax.persistence.*;

@Entity
@Getter
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id")
    private RentalHome rentalHome;
}
