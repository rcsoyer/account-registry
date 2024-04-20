package org.acme.accountregistry.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * User's credentials.
 * The User's data strictly related to the authentication process
 */
@Getter
@Embeddable
public class Principal {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

}
