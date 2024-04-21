package org.acme.accountregistry.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrincipalTest {

    @Test
    void testConstructor() {
        final var username = "    usern ame   ";
        final var password = "    pas  sw  ord";
        final var principal = new Principal(username, password);

        assertEquals("username", principal.getUsername());
        assertEquals("password", principal.getPassword());
    }

}