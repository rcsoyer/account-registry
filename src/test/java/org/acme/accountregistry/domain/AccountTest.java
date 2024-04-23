package org.acme.accountregistry.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.neovisionaries.i18n.CountryCode.NL;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
            assertEquals(personName, account.getName());
        }
    }

    @Test
    void addBankAccount() {
    }

    @Test
    void getBankAccounts() {
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

}