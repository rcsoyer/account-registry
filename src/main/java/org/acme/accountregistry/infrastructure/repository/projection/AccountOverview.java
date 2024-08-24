package org.acme.accountregistry.infrastructure.repository.projection;

import java.math.BigDecimal;
import java.util.Currency;

import jakarta.validation.constraints.NotNull;
import org.acme.accountregistry.domain.BankAccount;
import org.iban4j.Iban;

public interface AccountOverview {

    @NotNull
    Iban getAccountNumber();

    @NotNull
    BankAccount.Type getAccountType();

    @NotNull
    BigDecimal getBalance();

    @NotNull
    Currency getCurrency();
}
