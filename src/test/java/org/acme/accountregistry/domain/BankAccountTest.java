package org.acme.accountregistry.domain;

import java.math.BigDecimal;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import static org.acme.accountregistry.fixtures.DataUtils.account;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountTest {

    @Test
    void testConstructor() {
        final Account accountHolder = account();
        final var accountType = BankAccount.Type.PAYMENTS;
        final var bankAccount = new BankAccount(accountHolder, accountType);
        final CountryCode accountCountry = accountHolder.getAddress().getCountry();

        assertEquals(accountHolder, bankAccount.getAccountHolder());

        assertThat(accountHolder.getBankAccounts())
          .containsExactly(bankAccount);

        assertEquals(accountType, bankAccount.getType());
        assertEquals(BigDecimal.ZERO, bankAccount.getBalance());

        assertEquals(accountCountry.getCurrency(), bankAccount.getCurrency());
        assertEquals(accountCountry.getAlpha2(), bankAccount.getIban().getCountryCode().getAlpha2());
    }

}