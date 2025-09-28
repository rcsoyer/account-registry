package org.acme.accountregistry.domain.entity;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.iban4j.Iban;
import org.springframework.web.server.ResponseStatusException;

import static jakarta.persistence.EnumType.STRING;
import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PROTECTED;
import static org.iban4j.CountryCode.getByCode;
import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * The representation of a Bank Account and its intrinsically related details. <br>
 * The account number is here represented by a random generated IBAN. <br>
 * An Account Holder {@link Account} may have many Bank Accounts in the platform.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class BankAccount extends BaseAuditEntity {

    @NaturalId
    @Immutable
    @Column(updatable = false)
    @NotNull(message = "The account IBAN is mandatory")
    private Iban iban;

    @Immutable
    @Enumerated(STRING)
    @Column(updatable = false)
    @NotNull(message = "The account type is mandatory")
    private Type type;

    @Valid
    @NotNull(message = "The account balance is mandatory")
    private Money balance;

    @ManyToOne
    @Immutable
    @JoinColumn(name = "account_id", updatable = false)
    private Account accountHolder;

    public BankAccount(final Account accountHolder, final Type type) {
        this.accountHolder = accountHolder;
        this.type = type;
        final CountryCode accountCountry = accountHolder.getAddress().getCountry();
        this.balance = new Money(ZERO, accountCountry.getCurrency());
        this.iban = Iban.random(getByCode(accountCountry.getAlpha2()));
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final var that = (BankAccount) other;
        return getIban().equals(that.getIban());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIban());
    }

    /**
     * Debits the account balance by the given amount.
     *
     * @throws ResponseStatusException with 409 status code, if the account balance is not enough.
     */
    void debitMoney(final Money money) {
        final boolean dontHaveEnoughBalance =
          balance.getAmount().compareTo(money.getAmount()) < 0;
        if (dontHaveEnoughBalance) {
            throw new ResponseStatusException(CONFLICT, "Insufficient balance to send money");
        }

        balance.subtract(money);
    }

    void topUp(final Money money) {
        balance.add(money);
    }

    public String getAccountHolderName() {
        final PersonName personName = accountHolder.getPersonName();
        return personName.getInitials() + " " + personName.getLastName();
    }

    public enum Type {
        PAYMENTS,
        SAVINGS,
        FIXED_DEPOSIT
    }
}
