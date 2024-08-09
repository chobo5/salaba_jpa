package salaba.entity.rental;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
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
