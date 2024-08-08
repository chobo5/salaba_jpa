package salaba.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
public class Role extends BaseEntity {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(length = 30)
    private String roleName;

}
