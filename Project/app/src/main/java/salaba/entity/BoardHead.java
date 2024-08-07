package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "head")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class BoardHead {
    @Id
    @Column(name = "head_no")
    private int headNo;

    @Column(name = "head_name", unique = true, nullable = false)
    private String headName;
}
