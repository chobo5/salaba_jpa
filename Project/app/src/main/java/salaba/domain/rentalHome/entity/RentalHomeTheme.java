package salaba.domain.rentalHome.entity;

import lombok.Getter;
import salaba.domain.global.entity.BaseEntity;

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

    public static RentalHomeTheme create(RentalHome rentalHome, Theme theme) {
        RentalHomeTheme rentalHomeTheme = new RentalHomeTheme();
        rentalHomeTheme.rentalHome = rentalHome;
        rentalHomeTheme.theme = theme;
        return rentalHomeTheme;
    }
}
