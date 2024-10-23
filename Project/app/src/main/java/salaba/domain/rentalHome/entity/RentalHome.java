package salaba.domain.rentalHome.entity;

import lombok.Getter;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.BaseEntity;
import salaba.domain.global.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.constants.RentalHomeStatus;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private RentalHomeStatus status;

    @Column(nullable = false)
    private String rule;

    @Column(nullable = false)
    private int cleanFee;

    @OneToMany(mappedBy = "rentalHome", cascade = CascadeType.ALL)
    private List<RentalHomeTheme> rentalHomeThemes = new ArrayList<>();

    @OneToMany(mappedBy = "rentalHome", cascade = CascadeType.ALL)
    private List<RentalHomeFacility> rentalHomeFacilities = new ArrayList<>();

    @Column(nullable = false)
    private Double reviewAvg = 0.0;

    @Column(nullable = false)
    private Long reviewSum = 0L;

    @Column(nullable = false)
    private Long reviewCount = 0L;

    @Transient
    private Long salesCount;


    public static RentalHome create(Member host, Region region, String name, String explanation, Address address, int price, int capacity, double lat, double lon, String rule, int cleanFee) {
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
        rentalHome.status = RentalHomeStatus.RUN;
        rentalHome.rule = rule;
        rentalHome.cleanFee = cleanFee;
        return rentalHome;
    }

    public void modify(Region region, String name, String explanation, Address address, Integer price,
                       Integer capacity, Double lat, Double lon, String rule, Integer cleanFee) {
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
        this.status = RentalHomeStatus.DELETED;
    }

    public void updateReviewStatistics(int reviewScore) {
        reviewSum += reviewScore;
        reviewCount++;
        double avg = (double) reviewSum / reviewCount;
        reviewAvg = Math.round(avg * 100) / 100.0;

    }

    public void setSalesCount(Long salesCount) {
        this.salesCount = salesCount != null ? salesCount : 0;
    }


}
