package org.acme.accountregistry.domain.entity;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.iban4j.Iban;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static org.iban4j.CountryCode.getByCode;

/**
 * The representation of a Bank Account and its intrinsically related details. <br>
 * The account number is here represented by a random generated IBAN. <br>
 * An Account Holder {@link Account} may have many Bank Accounts in the platform.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class BankAccount extends AbstractIdentityEntity {

    @NaturalId
    @Immutable
    @Column(updatable = false)
    @NotNull(message = "The account IBAN is mandatory")
    private Iban iban;

    @Immutable
    @Enumerated(STRING)
    @Column(columnDefinition = "VARCHAR", updatable = false)
    @NotNull(message = "The account type is mandatory")
    private Type type;

    /**
     * Currency is inferred from the account holder's country.
     */
    @Immutable
    @Column(updatable = false)
    @NotNull(message = "The account currency is mandatory")
    private Currency currency;

    @NotNull(message = "The account balance is mandatory")
    private BigDecimal balance;

    @ManyToOne
    @Immutable
    @JoinColumn(name = "account_id", updatable = false)
    private Account accountHolder;

    public BankAccount(final Account accountHolder, final Type type) {
        this.accountHolder = accountHolder;
        this.type = type;
        this.balance = BigDecimal.ZERO;
        final CountryCode accountCountry = accountHolder.getAddress().getCountry();
        this.iban = Iban.random(getByCode(accountCountry.getAlpha2()));
        this.currency = accountCountry.getCurrency();
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

    public enum Type {
        PAYMENTS,
        SAVINGS,
        FIXED_DEPOSIT
    }
}
