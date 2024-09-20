package salaba.domain.rentalHome.entity;

import lombok.Getter;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.BaseEntity;
import salaba.domain.common.entity.Region;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.member.entity.Member;
import salaba.exception.CannotChangeStatusException;
import salaba.domain.rentalHome.constants.RentalHomeStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String rule;

    @Column(nullable = false)
    private int cleanFee;

    @OneToMany(mappedBy = "rentalHome", cascade = CascadeType.ALL)
    private Set<RentalHomeTheme> rentalHomeThemes = new HashSet<>();

    @OneToMany(mappedBy = "rentalHome", cascade = CascadeType.ALL)
    private Set<RentalHomeFacility> rentalHomeFacilities = new HashSet<>();

    @OneToMany(mappedBy = "rentalHome")
    private List<Reservation> reservations = new ArrayList<>();

    @Transient
    private Double reviewAvg;

    @Transient
    private Long reviewCount;

    @Transient
    private Long salesCount;


    public static RentalHome createRentalHome(Member host, Region region, String name, String explanation, Address address, int price, int capacity, double lat, double lon, String rule, int cleanFee) {
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
        rentalHome.rule = rule;
        rentalHome.cleanFee = cleanFee;
        return rentalHome;
    }

    public void modify(Region region, String name, String explanation, Address address, Integer price, Integer capacity, Double lat, Double lon, String rule, Integer cleanFee) {
        this.region = region != null ? region : this.region;
        this.name = name != null ? name : this.name;
        this.explanation = explanation != null ? explanation : this.explanation;
        this.address = address != null ? address : this.address;
        this.price = price != null ? price : this.price;
        this.capacity = capacity != null ? capacity : this.capacity;
        this.lat = lat != null ? lat : this.lat;
        this.lon = lon != null ? lon : this.lon;
        this.rule = rule != null ? rule : this.rule;
        this.cleanFee = cleanFee != null ? cleanFee : this.cleanFee;
    }

    public void setFacilities(List<RentalHomeFacility> facilities) {
        rentalHomeFacilities.clear();
        rentalHomeFacilities.addAll(facilities);
    }


    public void setThemes(List<RentalHomeTheme> themes) {
        rentalHomeThemes.clear();
        rentalHomeThemes.addAll(themes);
    }

    public void closeRentalHome() {
        reservations.forEach(reservation -> {
            if (reservation.getEndDate().isAfter(LocalDateTime.now())) {
                throw new CannotChangeStatusException("이용중이거나 예약된 게스트가 있어 삭제가 불가능합니다.");
            }
        });
        this.status = RentalHomeStatus.DELETED;
    }

    public void setReviewAvg(Double reviewAvg) {
        this.reviewAvg = reviewAvg != null ? (double) Math.round(reviewAvg * 100) / 100 : 0.0;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount != null ? reviewCount : 0;
    }

    public void setSalesCount(Long salesCount) {
        this.salesCount = salesCount != null ? salesCount : 0;
    }
}
