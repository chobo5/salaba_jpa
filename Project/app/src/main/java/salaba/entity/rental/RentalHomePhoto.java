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
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    private int photoOrder;

    public static RentalHomePhoto createRentalHomePhoto(String explanation, RentalHome rentalHome, int order) {
        RentalHomePhoto rentalHomePhoto = new RentalHomePhoto();
        rentalHomePhoto.photoExplanation = explanation;
        rentalHomePhoto.rentalHome = rentalHome;
        rentalHomePhoto.photoOrder = order;
        rentalHome.getRentalHomePhotoList().add(rentalHomePhoto);
        return rentalHomePhoto;
    }

}
