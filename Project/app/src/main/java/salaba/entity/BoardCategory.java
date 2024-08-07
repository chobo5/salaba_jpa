package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class BoardCategory {
    @Id
    @Column(name = "category_no")
    private int categoryNo;

    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;
}
