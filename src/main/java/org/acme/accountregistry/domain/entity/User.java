package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.ObjectUtils.anyNull;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/**
 * User's credentials.
 * The User's data strictly related to the authentication process
 */
@Getter
@Entity
@Table(name = "application_user")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseAuditEntity
  implements UserDetails, CredentialsContainer {

    @Serial
    private static final long serialVersionUID = 4989168819313182895L;

    @NaturalId
    @Immutable
    @Column(updatable = false)
    @Email(message = "The username must be an email")
    @NotBlank(message = "The Account username is mandatory")
    @Size(max = 50, message = "The username must be 50 characters or less")
    private String username;

    @NotBlank(message = "The account password is mandatory")
    @Size(max = 150, message = "The password must be 150 characters or less")
    private String password;

    private boolean enabled;

    public User(final String username, final String password) {
        this.username = deleteWhitespace(username);
        setPassword(password);
        enable();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final var that = (User) other;

        if (anyNull(getUsername(), that.getUsername())) {
            return false;
        }

        return getUsername().equalsIgnoreCase(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername().toUpperCase());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    public void setPassword(final String password) {
        this.password = deleteWhitespace(password);
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
