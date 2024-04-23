package org.acme.accountregistry.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.iban4j.Iban;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static org.iban4j.CountryCode.getByCode;

/**
 * The representation of a Bank Account and its intrinsically related details.
 * <br/> The account number is here represented by a random generated IBAN.
 * <br/> An Account Holder {@link Account} may have many Bank Accounts in the platform.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class BankAccount extends AbstractIdentityEntity {

    @NaturalId
    @NotNull(message = "The account IBAN is mandatory")
    @Convert(converter = IbanConverter.class)
    private Iban iban;

    @Enumerated(STRING)
    @Column(columnDefinition = "VARCHAR")
    @NotNull(message = "The account type is mandatory")
    private Type type;

    /**
     * Currency is inferred from the account holder's country.
     */
    @NotNull(message = "The account currency is mandatory")
    private Currency currency;

    @NotNull(message = "The account balance is mandatory")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountHolder;

    public BankAccount(final Account accountHolder, final Type type) {
        setAccountHolder(accountHolder);
        this.type = type;
        this.balance = BigDecimal.ZERO;
        final CountryCode accountCountry = accountHolder.getAddress().getCountry();
        this.iban = Iban.random(getByCode(accountCountry.getAlpha2()));
        this.currency = accountCountry.getCurrency();
    }

    /**
     * @implNote This setter must be kept private because there should not be a way to change the account holder.
     */
    private void setAccountHolder(final Account accountHolder) {
        this.accountHolder = accountHolder;
        this.accountHolder.addBankAccount(this);
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

        if (getIban() == null || that.getIban() == null) {
            return false;
        }

        return getIban().equals(that.getIban());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIban());
    }

    public enum Type {
        PAYMENTS, SAVINGS, FIXED_DEPOSIT
    }
}
