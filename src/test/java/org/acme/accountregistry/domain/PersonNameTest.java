package org.acme.accountregistry.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    void testConstructor(){
        final var name = PersonName.builder()
                                   .initials("    j      r")
                                   .firstName("  jOhn   ")
                                   .lastName("  doE")
                                   .build();

        assertEquals("JR", name.getInitials());
        assertEquals("John", name.getFirstName());
        assertEquals("Doe", name.getLastName());
    }

}