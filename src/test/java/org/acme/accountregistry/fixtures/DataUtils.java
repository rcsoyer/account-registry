package org.acme.accountregistry.fixtures;

import java.time.LocalDate;
import org.acme.accountregistry.domain.entity.Account;
import org.acme.accountregistry.domain.entity.Address;
import org.acme.accountregistry.domain.entity.BankAccount;
import org.acme.accountregistry.domain.entity.PersonName;
import org.acme.accountregistry.domain.entity.User;

import static com.neovisionaries.i18n.CountryCode.NL;

public final class DataUtils {

    private DataUtils() {
    }

    public static User user() {
        return new User("user@gmail.com", "password");
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
                      .user(user())
                      .address(address())
                      .personName(personName())
                      .build();
    }

    public static BankAccount bankAccount(final Account accountHolder) {
        return new BankAccount(accountHolder, BankAccount.Type.PAYMENTS);
    }
}
