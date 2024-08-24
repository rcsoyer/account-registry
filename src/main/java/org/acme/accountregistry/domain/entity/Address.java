package org.acme.accountregistry.domain.entity;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.server.ResponseStatusException;

import static com.neovisionaries.i18n.CountryCode.BE;
import static com.neovisionaries.i18n.CountryCode.NL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The representation of a person's Address and its intrinsically related details.
 * <br/> The mapping in the database is such that each {@link Address} record is associated with only one
 * {@link Account} record.
 * <br/> Therefore, this also allow for further optimizations in the database schema, that the {@link Address} table
 * has as primary key the same value as the {@link Account#getId()}.
 */
@Getter
@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Address extends AbstractIdEntity {

    @NotBlank(message = "The street is mandatory")
    @Size(max = 100, message = "The street must be 100 characters or less")
    private String street;

    @NotBlank(message = "The city name is mandatory")
    @Size(max = 60, message = "The city name must be 60 characters or less")
    private String city;

    @NotBlank(message = "Zip Code is mandatory")
    @Size(max = 30, message = "The zip code must be 30 characters or less")
    private String zipCode;

    @Enumerated(STRING)
    @Column(columnDefinition = "VARCHAR")
    @NotNull(message = "The country is mandatory")
    private CountryCode country;

    @MapsId
    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id", updatable = false)
    private Account account;

    @Builder
    private Address(final String street,
                    final String city,
                    final String zipCode,
                    final CountryCode country) {
        setStreet(street);
        setCity(city);
        setZipCode(zipCode);
        setCountry(country);
    }

    private void setStreet(final String street) {
        this.street = capitalize(normalizeSpace(street));
    }

    private void setCity(final String city) {
        this.city = capitalize(normalizeSpace(city));
    }

    private void setZipCode(final String zipCode) {
        this.zipCode = upperCase(deleteWhitespace(zipCode));
    }

    private void setCountry(final CountryCode country) {
        if (country != NL && country != BE) {
            throw new ResponseStatusException(BAD_REQUEST, "Only people from the Netherlands or Belgium can open an account");
        }

        this.country = country;
    }

    /**
     * In order to set the bidirectional relationship between the {@link Account} and the {@link Address} entities.
     */
    void setAccount(final Account account) {
        this.account = account;
    }
}
