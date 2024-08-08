package salaba.entity;

import lombok.*;
import salaba.entity.board.Reply;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "nation")
@Getter
public class Nation extends BaseEntity {
    @Id
    @Column(name = "nation_no")
    private Integer nationNo;

    @Column(name = "nation_name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "nation")
    private List<Region> regionList;


}
