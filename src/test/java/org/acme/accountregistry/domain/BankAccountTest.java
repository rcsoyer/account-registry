package org.acme.accountregistry.domain;

import java.math.BigDecimal;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.acme.accountregistry.fixtures.DataUtils.account;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Nested
    class TestEqualsAndHashCode {

        @Test
        void testEqualsAndHashCode_whenSameIbanThenEquals() {
            final Account accountHolder = account();
            final var bankAccount1 = new BankAccount(accountHolder, BankAccount.Type.PAYMENTS);
            final var bankAccount2 = new BankAccount(accountHolder, BankAccount.Type.PAYMENTS);
            ReflectionTestUtils.setField(bankAccount2, "iban", bankAccount1.getIban());

            assertEquals(bankAccount1, bankAccount2);
            assertEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
        }

        @Test
        void testEqualsAndHashCode_whenDifferentIbanThenNotEquals() {
            final Account accountHolder = account();
            final var bankAccount1 = new BankAccount(accountHolder, BankAccount.Type.SAVINGS);
            final var bankAccount2 = new BankAccount(accountHolder, BankAccount.Type.SAVINGS);

            assertNotEquals(bankAccount1, bankAccount2);
            assertNotEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
        }
    }
}