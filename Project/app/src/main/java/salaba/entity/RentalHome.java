package salaba.entity;

import lombok.Getter;
import salaba.vo.Region;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_home")
@Getter
public class RentalHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_home_no", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @Column(nullable = false)
    private Member host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_no")
    @Column(nullable = false)
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
    private LocalDateTime regDate;

    @Column(nullable = false)
    private String rule;

    @Column(nullable = false)
    private int cleanFee;


}
