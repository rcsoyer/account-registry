package org.acme.accountregistry.fixtures;

import java.time.LocalDate;

import org.acme.accountregistry.domain.Account;
import org.acme.accountregistry.domain.Address;
import org.acme.accountregistry.domain.BankAccount;
import org.acme.accountregistry.domain.PersonName;
import org.acme.accountregistry.domain.Principal;

import static com.neovisionaries.i18n.CountryCode.NL;

public final class DataUtils {

    private DataUtils() {
    }

    public static Principal principal() {
        return new Principal("username", "password");
    }

    public static Address address() {
        return Address
                 .builder()
                 .street("123 main st")
                 .city("New York")
                 .zipCode("10001BA")
                 .country(NL)
                 .build();
    }

    public static PersonName personName() {
        return PersonName
                 .builder()
                 .initials("VD")
                 .firstName("Van der")
                 .lastName("Madeweg")
                 .build();
    }

    public static Account account() {
        return Account.builder()
                      .birthDate(LocalDate.now().minusYears(18))
                      .idDocument("1234567890ETG")
                      .principal(principal())
                      .address(address())
                      .name(personName())
                      .build();
    }

    public static BankAccount bankAccount(final Account accountHolder) {
        return new BankAccount(accountHolder, BankAccount.Type.PAYMENTS);
    }
}
