package salaba.util;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN(1), MANAGER(2), MEMBER(3);

    private final int id;

    RoleName(int id) {
        this.id = id;
    }
}
