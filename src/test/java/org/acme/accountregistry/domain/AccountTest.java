package org.acme.accountregistry.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static com.neovisionaries.i18n.CountryCode.NL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Nested
    class TestConstructor {

        @Test
        void testConstructor_whenLegalAgeThenSuccess() {
            final var legalAge = LocalDate.now().minusYears(18);
            final var idDocument = "1234567890ETG";
            final Principal principal = principal();
            final Address address = address();
            final PersonName personName = personName();
            final var account = Account.builder()
                                       .birthDate(legalAge)
                                       .idDocument(idDocument)
                                       .principal(principal)
                                       .address(address)
                                       .name(personName)
                                       .build();

            assertEquals(legalAge, account.getBirthDate());
            assertEquals(idDocument, account.getIdDocument());
            assertEquals(principal, account.getPrincipal());
            assertEquals(address, account.getAddress());
            assertEquals(account, address.getAccount());
            assertEquals(personName, account.getName());
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
                                      .name(personName())
                                      .build());
        }
    }

    @Test
    void addBankAccount() {
        final Account accountHolder = account();
        final var bankAccount = new BankAccount(accountHolder, BankAccount.Type.PAYMENTS);

        assertThat(accountHolder.getBankAccounts())
          .containsExactly(bankAccount);
        assertEquals(accountHolder, bankAccount.getAccountHolder());
    }

    @Test
    void getBankAccounts() {
        assertThrows(UnsupportedOperationException.class,
                     () -> account().getBankAccounts().clear());
    }

    private Principal principal() {
        return new Principal("username", "password");
    }

    private Address address() {
        return Address
                 .builder()
                 .street("123 main st")
                 .city("New York")
                 .zipCode("10001BA")
                 .country(NL)
                 .build();
    }

    private PersonName personName() {
        return PersonName
                 .builder()
                 .initials("VD")
                 .firstName("Van der")
                 .lastName("Madeweg")
                 .build();
    }

    private Account account() {
        return Account.builder()
                      .birthDate(LocalDate.now().minusYears(18))
                      .idDocument("1234567890ETG")
                      .principal(principal())
                      .address(address())
                      .name(personName())
                      .build();
    }

}