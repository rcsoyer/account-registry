package org.acme.accountregistry.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    void testConstructor(){
        final var name = PersonName.builder()
                                   .initials("    J.      R.")
                                   .firstName("  Jon  E ")
                                   .lastName("  Doe")
                                   .build();

        assertEquals("J.R.", name.getInitials());
        assertEquals("Jon E", name.getFirstName());
        assertEquals("Doe", name.getLastName());
    }

}