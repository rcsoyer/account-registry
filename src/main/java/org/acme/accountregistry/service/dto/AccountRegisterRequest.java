package org.acme.accountregistry.service.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * The DTO command to register/create a new Account into the system.
 * <br/> The data passes through basic validation and sanitization.
 */
public record AccountRegisterRequest(@NotBlank(message = "The Account username is mandatory")
                                     @Size(max = 50, message = "The username must be 50 characters or less")
                                     String username,
                                     @Past(message = "The birthdate must be in the past")
                                     @NotNull(message = "A person's birthdate is mandatory")
                                     LocalDate birthDate,
                                     @Size(max = 150, message = "The ID document must be 150 characters or less")
                                     @NotBlank(message = "A person's ID document is mandatory")
                                     String idDocument,
                                     @Valid AddressDto address,
                                     @Valid PersonNameDto personName) {

    public AccountRegisterRequest(final String username,
                                  final LocalDate birthDate,
                                  final String idDocument,
                                  final AddressDto address,
                                  final PersonNameDto personName) {
        this.username = trimToNull(username);
        this.birthDate = birthDate;
        this.idDocument = trimToNull(idDocument);
        this.address = address;
        this.personName = personName;
    }
}
