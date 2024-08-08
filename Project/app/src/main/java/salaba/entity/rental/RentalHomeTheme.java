package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomeTheme extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_theme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

}
