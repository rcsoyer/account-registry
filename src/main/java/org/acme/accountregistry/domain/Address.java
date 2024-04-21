package org.acme.accountregistry.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "The street is mandatory")
    private String street;

    @NotBlank(message = "The city name is mandatory")
    private String city;

    //TODO consider validation of zip code format per country
    @Column(name = "zip_code")
    @NotBlank(message = "Zip Code is mandatory")
    private String zipCode;

    @Enumerated(STRING)
    @Column(columnDefinition = "VARCHAR")
    @NotBlank(message = "The country is mandatory")
    private Country country;

    @MapsId
    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private Account account;

    @Builder
    private Address(final String street,
                    final String city,
                    final String zipCode,
                    final Country country) {
        this.street = normalizeSpace(street);
        this.city = normalizeSpace(city);
        this.zipCode = deleteWhitespace(zipCode);
        this.country = country;
    }

    /**
     * In order to set the bidirectional relationship between the {@link Account} and the {@link Address} entities.
     */
    void setAccount(final Account account) {
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
