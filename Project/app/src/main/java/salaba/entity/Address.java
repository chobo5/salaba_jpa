package salaba.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {
    private String street;
    private int zipcode;
}
