package salaba.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {
    private String street;
    private int zipcode;

    public Address(String street, int zipcode) {
        this.street = street;
        this.zipcode = zipcode;
    }
}
