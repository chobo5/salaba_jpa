package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.FileBaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomePhoto extends FileBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_photo_id")
    private Long id;

    private String photoExplanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id")
    private RentalHome rentalHome;

    private int photoOrder;

}
