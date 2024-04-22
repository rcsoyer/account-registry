package org.acme.accountregistry.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDate.now;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * A person's Account details with the bank.
 * <br/> It's a registry of a person's banking account details.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Account extends AbstractIdentityEntity {

    @Valid
    @Embedded
    @NotNull(message = "A person's name is mandatory")
    private PersonName name;

    @Column(name = "birth_date")
    @Past(message = "The birthdate must be in the past")
    @NotNull(message = "A person's birthdate is mandatory")
    private LocalDate birthDate;

    @NotBlank(message = "A person's ID document is mandatory")
    @Column(name = "id_document")
    private String idDocument;

    @Valid
    @Embedded
    @NotNull(message = "The account credentials are mandatory")
    private Principal principal;

    @Valid
    @NotNull(message = "The account address is mandatory")
    @OneToOne(mappedBy = "account", cascade = ALL, fetch = LAZY)
    private Address address;

    @OneToOne(mappedBy = "accountHolder", cascade = ALL, fetch = LAZY)
    private BankAccount bankAccount;

    @Builder
    private Account(final PersonName name,
                    final LocalDate birthDate,
                    final String idDocument,
                    final Principal principal,
                    final Address address) {
        this.name = name;
        setBirthDate(birthDate);
        setIdDocument(idDocument);
        this.principal = principal;
        this.address = address;
        this.address.setAccount(this);
    }

    private void setBirthDate(final LocalDate birthDate) {
        if (birthDate != null) {
            final int personsAge = Period.between(birthDate, now()).getYears();
            final boolean isAdult = personsAge >= 18;

            if (!isAdult) {
                throw new ResponseStatusException(BAD_REQUEST, "The person must be an adult to open an account");
            }
        }

        this.birthDate = birthDate;
    }

    private void setIdDocument(final String idDocument) {
        this.idDocument = deleteWhitespace(idDocument);
    }

    void setBankAccount(final BankAccount bankAccount) {
        this.bankAccount = bankAccount;
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
