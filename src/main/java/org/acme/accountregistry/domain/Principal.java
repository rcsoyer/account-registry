package org.acme.accountregistry.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/**
 * User's credentials.
 * The User's data strictly related to the authentication process
 */
@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Principal {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Principal(final String username, final String password) {
        this.username = deleteWhitespace(username);
        this.password = deleteWhitespace(password);
    }
}
