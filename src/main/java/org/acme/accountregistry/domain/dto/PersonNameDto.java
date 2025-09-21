package org.acme.accountregistry.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.trimToNull;

public record PersonNameDto(@NotBlank(message = "The name initials are mandatory")
                            @Size(max = 10, message = "The name initials must be 10 characters or less")
                            String initials,
                            @NotBlank(message = "The first name is mandatory")
                            @Size(max = 60, message = "The first name must be 60 characters or less")
                            String firstName,
                            @NotBlank(message = "The last name is mandatory")
                            @Size(max = 60, message = "The last name must be 60 characters or less")
                            String lastName)
  implements Serializable {

    @Serial
    private static final long serialVersionUID = -7249279568122138068L;

    public PersonNameDto(final String initials, final String firstName, final String lastName) {
        this.initials = trimToNull(initials);
        this.firstName = trimToNull(firstName);
        this.lastName = trimToNull(lastName);
    }
}
