package salaba.entity.member;

import lombok.Getter;

@Getter
public enum RoleName {
    MEMBER(1), MANAGER(2), ADMIN(3);

    private final int id;

    RoleName(int id) {
        this.id = id;
    }
}
