package org.acme.accountregistry.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * A person's Account details with the bank.
 * <br/> It's a registry of a person's banking account details.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Account extends AbstractIdentityEntity {

    @NotBlank(message = "A person's Name is mandatory")
    private String name;

    @NotBlank(message = "A person's birthdate is mandatory")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = "A person's ID document is mandatory")
    @Column(name = "id_document")
    private String idDocument;

    private Principal principal;

    @OneToOne(mappedBy = "account", cascade = ALL, fetch = LAZY)
    private Address address;

    @Builder
    private Account(final String name,
                    final LocalDate birthDate,
                    final String idDocument,
                    final Principal principal,
                    final Address address) {
        this.name = name;
        this.birthDate = birthDate;
        this.idDocument = idDocument;
        this.principal = principal;
        this.address = address;
    }

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
