package salaba.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_scope")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class BoardScope {

    @Id
    @Column(name = "scope_no")
    private int scopeNo;

    @Column(name = "scope_name", unique = true, nullable = false)
    private String scopeName;
}
