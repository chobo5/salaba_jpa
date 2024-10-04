package salaba.domain.global.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {
    private String street;
    private Integer zipcode;

    public Address(String street, int zipcode) {
        this.street = street;
        this.zipcode = zipcode;
    }
}
