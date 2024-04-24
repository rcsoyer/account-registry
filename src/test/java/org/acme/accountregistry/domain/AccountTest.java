package org.acme.accountregistry.domain;

import java.time.LocalDate;

import org.acme.accountregistry.fixtures.DataUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.acme.accountregistry.fixtures.DataUtils.account;
import static org.acme.accountregistry.fixtures.DataUtils.address;
import static org.acme.accountregistry.fixtures.DataUtils.personName;
import static org.acme.accountregistry.fixtures.DataUtils.principal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Nested
    class TestConstructor {

        @Test
        void testConstructor_whenLegalAgeThenSuccess() {
            final var legalAge = LocalDate.now().minusYears(18);
            final Principal principal = principal();
            final Address address = address();
            final PersonName personName = personName();
            final var account = Account.builder()
                                       .birthDate(legalAge)
                                       .idDocument("  1234567  890ETG  ")
                                       .principal(principal)
                                       .address(address)
                                       .personName(personName)
                                       .build();

            assertEquals(legalAge, account.getBirthDate());
            assertEquals("1234567890ETG", account.getIdDocument());
            assertEquals(principal, account.getPrincipal());
            assertEquals(address, account.getAddress());
            assertEquals(account, address.getAccount());
            assertEquals(personName, account.getPersonName());
        }

        @Test
        void testConstructor_whenNotLegalAgeThenError() {
            final var notLegalAge = LocalDate.now().minusYears(17);
            assertThrows(ResponseStatusException.class,
                         () -> Account.builder()
                                      .birthDate(notLegalAge)
                                      .idDocument("1234567890ETG")
                                      .principal(principal())
                                      .address(address())
                                      .personName(personName())
                                      .build());
        }
    }

    @Test
    void addBankAccount() {
        final Account accountHolder = account();
        final var bankAccount = DataUtils.bankAccount(accountHolder);

        assertThat(accountHolder.getBankAccounts())
          .containsExactly(bankAccount);
        assertEquals(accountHolder, bankAccount.getAccountHolder());
    }

    @Test
    void getBankAccounts() {
        assertThrows(UnsupportedOperationException.class,
                     () -> account().getBankAccounts().clear());
    }

}