package org.acme.accountregistry.domain;

import org.junit.jupiter.api.Test;

import static com.neovisionaries.i18n.CountryCode.NL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void testConstructor() {
        final var address = Address.builder()
                                   .street("  123  mAin  st  ")
                                   .city("  new  york  ")
                                   .zipCode("  10001 BA ")
                                   .country(NL)
                                   .build();

        assertEquals("123 Main St", address.getStreet());
        assertEquals("New York", address.getCity());
        assertEquals("10001BA", address.getZipCode());
        assertEquals(NL, address.getCountry());
    }
}