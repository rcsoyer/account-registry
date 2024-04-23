package org.acme.accountregistry.domain;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.neovisionaries.i18n.CountryCode.NL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @ParameterizedTest
    @ValueSource(strings = {"NL", "BE"})
    void testConstructor_whenCountriesAllowedThenSuccess(final String countryCode) {
        final var address = Address.builder()
                                   .street("  123  main  st  ")
                                   .city("  New  York  ")
                                   .zipCode("  10001 BA ")
                                   .country(CountryCode.valueOf(countryCode))
                                   .build();

        assertEquals("123 main st", address.getStreet());
        assertEquals("New York", address.getCity());
        assertEquals("10001BA", address.getZipCode());
        assertEquals(CountryCode.valueOf(countryCode), address.getCountry());
    }
}