package org.acme.accountregistry.domain.dto.query;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import org.acme.accountregistry.domain.entity.BankAccount;
import org.iban4j.Iban;

public interface AccountOverview extends Serializable {

    @NotNull
    Iban getAccountNumber();

    @NotNull
    BankAccount.Type getAccountType();

    @NotNull
    BigDecimal getBalance();

    @NotNull
    Currency getCurrency();
}
