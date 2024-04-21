package org.acme.accountregistry.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Address extends AbstractIdEntity {

    private String street;

    private String city;

    //TODO consider validation of zip code format per country
    @Column(name = "zip_code")
    private String zipCode;

    @Enumerated(STRING)
    @Column(columnDefinition = "VARCHAR")
    private Country country;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private Account account;

    @Builder
    private Address(final String street,
                    final String city,
                    final String zipCode,
                    final Country country,
                    final Account account) {
        this.street = normalizeSpace(street);
        this.city = normalizeSpace(city);
        this.zipCode = deleteWhitespace(zipCode);
        this.country = country;
        this.account = account;
    }

    @Getter
    @RequiredArgsConstructor(access = PRIVATE)
    public enum Country {
        NL("THE NETHERLANDS"),
        BE("BELGIUM");

        private final String title;
    }
}
