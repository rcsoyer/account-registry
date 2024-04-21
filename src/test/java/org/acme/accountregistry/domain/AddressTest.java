package org.acme.accountregistry.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void testConstructor() {
        final var address = Address.builder()
                                   .street("  123  mAin  st  ")
                                   .city("  new  york  ")
                                   .zipCode("  10001 BA ")
                                   .build();

        assertEquals("123 Main St", address.getStreet());
        assertEquals("New York", address.getCity());
        assertEquals("10001BA", address.getZipCode());
    }
}