package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "nation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class Nation {
    @Id
    @Column(name = "nation_no")
    private Integer nationNo;

    @Column(name = "nation_name", unique = true, nullable = false)
    private String nationName;
}
