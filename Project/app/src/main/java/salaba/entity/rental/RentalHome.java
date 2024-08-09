package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.Address;
import salaba.entity.BaseEntity;
import salaba.entity.Region;
import salaba.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_home")
@Getter
public class RentalHome extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lon;

    @Column(nullable = false)
    private RentalHomeStatus status;

    @Column(nullable = false)
    private LocalDateTime hostingStartDate;

    @Column(nullable = false)
    private LocalDateTime hostingEndDate;

    @Column(nullable = false)
    private String rule;

    @Column(nullable = false)
    private int cleanFee;

    @OneToMany(mappedBy = "rentalHome")
    private List<RentalHomePhoto> rentalHomePhotoList = new ArrayList<>();

    @OneToMany(mappedBy = "rentalHome")
    private List<RentalHomeTheme> rentalHomeThemeList = new ArrayList<>();

    @OneToMany(mappedBy = "rentalHome")
    private List<RentalHomeFacility> rentalHomeFacilityList = new ArrayList<>();

    @OneToMany(mappedBy = "rentalHome")
    private List<RentalHomeReport> rentalHomeReportList = new ArrayList<>();

    @OneToMany(mappedBy = "rentalHome")
    private List<Reservation> reservationList = new ArrayList<>();

    public static RentalHome createRentalHome(Member host, Region region, String name, String explanation, Address address, int price, int capacity, double lat, double lon, LocalDateTime hostingStartDate, LocalDateTime hostingEndDate, String rule, int cleanFee) {
        RentalHome rentalHome = new RentalHome();
        rentalHome.host = host;
        rentalHome.region = region;
        rentalHome.name = name;
        rentalHome.explanation = explanation;
        rentalHome.address = address;
        rentalHome.price = price;
        rentalHome.capacity = capacity;
        rentalHome.lat = lat;
        rentalHome.lon = lon;
        rentalHome.status = RentalHomeStatus.AWAIT;
        rentalHome.hostingStartDate = hostingStartDate;
        rentalHome.hostingEndDate = hostingEndDate;
        rentalHome.rule = rule;
        rentalHome.cleanFee = cleanFee;
        return rentalHome;
    }
}
