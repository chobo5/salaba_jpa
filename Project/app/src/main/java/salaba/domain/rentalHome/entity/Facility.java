package salaba.domain.rentalHome.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility {
    @Id
    @GeneratedValue
    @Column(name = "facility_id")
    private Long id;

    @Column(name = "facility_name", nullable = false, unique = true)
    private String name;

    public Facility (String name) {
        this.name = name;
    }
}
