package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "grade")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Grade {
    @Id
    @Column(name = "grade_no")
    private Integer gradeNo;

    @Column(name = "grade_name", unique = true, nullable = false)
    private String gradeName;
}
