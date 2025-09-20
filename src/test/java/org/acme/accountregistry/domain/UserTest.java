package org.acme.accountregistry.domain;

import org.acme.accountregistry.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testConstructor() {
        final var username = "    usern ame   ";
        final var password = "    pas  sw  ord";
        final var user = new User(username, password);

        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

}