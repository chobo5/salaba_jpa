package salaba.entity;

import lombok.*;
import salaba.entity.board.Reply;

import javax.persistence.*;
import java.util.List;

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
    private List<Region> regionList;

    public Nation(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
