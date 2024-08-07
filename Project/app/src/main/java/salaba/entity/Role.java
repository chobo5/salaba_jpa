package salaba.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role {
    @Id
    @Column(name = "role_no")
    private Integer roleNo;

    @Column(length = 30)
    private String roleName;

    @Override
    public String toString() {
        return "Role{" +
                "roleNo=" + roleNo +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
