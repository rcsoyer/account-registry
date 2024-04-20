package org.acme.accountregistry.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import lombok.Getter;

/**
 * A person's Account details with the bank.
 * <br/> It's a registry of a person's banking account details.
 */
@Getter
@Entity
public class Account extends AbstractEntity {

    private String name;

    private String address;

    private LocalDate birthDate;

    private String idDocument;

    private Principal principal;

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final var that = (Account) other;

        if (getId() == null || that.getId() == null) {
            return false;
        }

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
