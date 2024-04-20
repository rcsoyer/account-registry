package org.acme.accountregistry.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@Embeddable
public class Address {

    private String street;

    private String city;

    //TODO consider validation of zip code format per country
    private String zipCode;

    private Country country;

    @Getter
    @RequiredArgsConstructor(access = PRIVATE)
    public enum Country {
        NL("THE NETHERLANDS"),
        BE("BELGIUM");

        private final String title;
    }
}
