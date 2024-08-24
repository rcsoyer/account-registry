package org.acme.accountregistry.domain.dto.query;

import java.math.BigDecimal;
import java.util.Currency;

import jakarta.validation.constraints.NotNull;
import org.acme.accountregistry.domain.entity.BankAccount;
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
