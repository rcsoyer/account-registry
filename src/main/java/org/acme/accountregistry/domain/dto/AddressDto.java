package org.acme.accountregistry.domain.dto;

import com.neovisionaries.i18n.CountryCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.trimToNull;

public record AddressDto(@NotBlank(message = "The street is mandatory")
                         @Size(max = 100, message = "The street must be 100 characters or less")
                         String street,
                         @NotBlank(message = "The city name is mandatory")
                         @Size(max = 60, message = "The city name must be 60 characters or less")
                         String city,
                         @NotBlank(message = "Zip Code is mandatory")
                         @Size(max = 30, message = "The zip code must be 30 characters or less")
                         String zipCode,
                         @NotNull(message = "The country is mandatory")
                         CountryCode country)
  implements Serializable {

    @Serial
    private static final long serialVersionUID = -8473928647365912834L;

    public AddressDto(final String street, final String city, final String zipCode, final CountryCode country) {
        this.street = trimToNull(street);
        this.city = trimToNull(city);
        this.zipCode = trimToNull(zipCode);
        this.country = country;
    }
}
