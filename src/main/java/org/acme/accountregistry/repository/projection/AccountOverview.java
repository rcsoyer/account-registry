package org.acme.accountregistry.repository.projection;

import java.math.BigDecimal;
import java.util.Currency;

import org.acme.accountregistry.domain.BankAccount;
import org.iban4j.Iban;

public interface AccountOverview {

    Iban getAccountNumber();

    BankAccount.Type getAccountType();

    BigDecimal getBalance();

    Currency getCurrency();
}
