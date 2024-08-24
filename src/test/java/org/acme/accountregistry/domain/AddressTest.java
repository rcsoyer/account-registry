package org.acme.accountregistry.domain;

import java.util.stream.Stream;

import com.neovisionaries.i18n.CountryCode;
import org.acme.accountregistry.domain.entity.Address;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.server.ResponseStatusException;

import static com.neovisionaries.i18n.CountryCode.BE;
import static com.neovisionaries.i18n.CountryCode.NL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    @Nested
    class TestConstructor {

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

        @ParameterizedTest
        @MethodSource("notAllowedCountries")
        void testConstructor_whenCountriesNotAllowedThenError(final CountryCode countryCode) {
            assertThrows(ResponseStatusException.class,
                         () -> Address.builder()
                                      .country(countryCode)
                                      .build());
        }

        private static Stream<CountryCode> notAllowedCountries() {
            return Stream.of(CountryCode.values())
                         .filter(countryCode -> countryCode != NL && countryCode != BE);
        }

    }
}