package salaba.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id", nullable = false)
    private Nation nation;
}
