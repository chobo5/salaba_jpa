package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.Address;
import salaba.entity.BaseEntity;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.exception.CannotChangeStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static salaba.entity.rental.QRentalHome.rentalHome;

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
    private Set<RentalHomeTheme> rentalHomeThemeSet = new HashSet<>();

    @OneToMany(mappedBy = "rentalHome")
    private Set<RentalHomeFacility> rentalHomeFacilitySet = new HashSet<>();

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

    public void modifyRentalHome(Region region, String name, String explanation, Address address, int price, int capacity, double lat, double lon, LocalDateTime hostingStartDate, LocalDateTime hostingEndDate, String rule, int cleanFee) {
        this.region = region;
        this.name = name;
        this.explanation = explanation;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
        this.lat = lat;
        this.lon = lon;
        this.hostingStartDate = hostingStartDate;
        this.hostingEndDate = hostingEndDate;
        this.rule = rule;
        this.cleanFee = cleanFee;
    }

    public void setFacilities(List<RentalHomeFacility> facilities) {
        rentalHomeFacilitySet.clear();
        rentalHomeFacilitySet.addAll(facilities);
    }


    public void setThemes(List<RentalHomeTheme> themes) {
        rentalHomeThemeSet.clear();
        rentalHomeThemeSet.addAll(themes);
    }

    public void closeRentalHome() {
        reservationList.forEach(reservation -> {
            if (reservation.getEndDate().isAfter(LocalDateTime.now())) {
                throw new CannotChangeStatusException("이용중이거나 예약된 게스트가 있어 삭제가 불가능합니다.");
            }
        });
        this.status = RentalHomeStatus.DELETED;
    }
}
