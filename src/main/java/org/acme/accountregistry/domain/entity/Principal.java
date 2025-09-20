package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

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

    @NaturalId
    @Immutable
    @NotBlank(message = "The Account username is mandatory")
    @Size(max = 50, message = "The username must be 50 characters or less")
    private String username;

    @Size(max = 150)
    @NotBlank(message = "The account password is mandatory")
    private String password;

    public Principal(final String username, final String password) {
        this.username = deleteWhitespace(username);
        this.password = deleteWhitespace(password);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final var that = (Principal) other;

        if (getUsername() == null || that.getUsername() == null) {
            return false;
        }

        return getUsername().equalsIgnoreCase(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername().toUpperCase());
    }
}
