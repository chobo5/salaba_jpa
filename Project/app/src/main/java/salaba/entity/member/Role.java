package salaba.entity.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import salaba.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Role {
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(length = 30, nullable = false, unique = true)
    private String roleName;
}
