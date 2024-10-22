package salaba.domain.rentalHome.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Theme(String name) {
        this.name = name;
    }
}
