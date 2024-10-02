package salaba.global.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nation extends BaseEntity {
    @Id
    @Column(name = "nation_id")
    private Integer id;

    @Column(name = "nation_name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "nation")
    private Set<Region> regions = new HashSet<>();

    public Nation(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
