package org.acme.accountregistry.domain;

import org.acme.accountregistry.domain.entity.PersonName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    void testConstructor(){
        final var name = PersonName.builder()
                                   .initials(" v d ")
                                   .firstName("  van  der  ")
                                   .lastName("  madeweg ")
                                   .build();

        assertEquals("VD", name.getInitials());
        assertEquals("Van der", name.getFirstName());
        assertEquals("Madeweg", name.getLastName());
    }

}