package org.acme.accountregistry.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDate.now;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

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

    @Column(name = "birth_date")
    @Past(message = "The birthdate must be in the past")
    @NotNull(message = "A person's birthdate is mandatory")
    private LocalDate birthDate;

    @NotBlank(message = "A person's ID document is mandatory")
    @Column(name = "id_document")
    private String idDocument;

    @Valid
    @NotNull(message = "The account credentials are mandatory")
    private Principal principal;

    @Valid
    @NotNull(message = "The account address is mandatory")
    @OneToOne(mappedBy = "account", cascade = ALL, fetch = LAZY)
    private Address address;

    @Builder
    private Account(final String name,
                    final LocalDate birthDate,
                    final String idDocument,
                    final Principal principal,
                    final Address address) {
        this.name = normalizeSpace(name);
        setBirthDate(birthDate);
        this.idDocument = deleteWhitespace(idDocument);
        this.principal = principal;
        this.address = address;
        this.address.setAccount(this);
    }

    private void setBirthDate(final LocalDate birthDate) {
        if (birthDate != null) {
            final int personsAge = Period.between(birthDate, now()).getYears();
            final boolean isAdult = personsAge >= 18;

            if (!isAdult) {
                throw new IllegalArgumentException("The person must be an adult to open an account");
            }
        }

        this.birthDate = birthDate;
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
