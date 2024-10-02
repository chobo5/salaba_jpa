package salaba.domain.auth.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import salaba.domain.auth.constant.RoleName;

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
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

}
