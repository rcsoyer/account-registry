package org.acme.accountregistry.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.iban4j.Iban;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static org.iban4j.CountryCode.getByCode;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class BankAccount extends AbstractIdentityEntity {

    @NaturalId
    @NotNull(message = "The account IBAN is mandatory")
    @Convert(converter = IbanConverter.class)
    private Iban iban;

    @NotNull(message = "The account type is mandatory")
    @Enumerated(STRING)
    private Type type;

    @NotNull(message = "The account currency is mandatory")
    private Currency currency;

    @NotNull(message = "The account balance is mandatory")
    private BigDecimal balance;

    @OneToOne
    private Account accountHolder;

    @Builder
    public BankAccount(final Account accountHolder,
                       final Type type,
                       final BigDecimal initialBalance) {
        this.accountHolder = accountHolder;
        this.accountHolder.setBankAccount(this);
        this.type = type;
        this.balance = initialBalance;
        final String accountCountry = accountHolder.getAddress().getCountry().name();
        this.iban = Iban.random(getByCode(accountCountry));
        this.currency = Currency.getInstance(accountHolder.getAddress().getCountry().getCurrencyCode());
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
