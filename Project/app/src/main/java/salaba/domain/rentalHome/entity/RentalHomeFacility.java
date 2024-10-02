package salaba.domain.rentalHome.entity;

import lombok.Getter;
import salaba.global.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
public class RentalHomeFacility extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_facility_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_home_id", nullable = false)
    private RentalHome rentalHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    public static RentalHomeFacility create(RentalHome rentalHome, Facility facility) {
        RentalHomeFacility rentalHomeFacility = new RentalHomeFacility();
        rentalHomeFacility.rentalHome = rentalHome;
        rentalHomeFacility.facility = facility;
        rentalHome.getRentalHomeFacilities().add(rentalHomeFacility);
        return rentalHomeFacility;
    }
}
