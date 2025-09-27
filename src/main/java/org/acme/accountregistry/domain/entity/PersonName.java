package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.apache.commons.text.WordUtils.capitalize;

/**
 * A person's name.
 * <br/> A person name is usually defined by: initials, a first name and a last name.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PersonName {

    @NotBlank(message = "The name initials are mandatory")
    @Size(max = 10, message = "The name initials must be 10 characters or less")
    private String initials;

    @NotBlank(message = "The first name is mandatory")
    @Size(max = 60, message = "The first name must be 60 characters or less")
    private String firstName;

    @NotBlank(message = "The last name is mandatory")
    @Size(max = 60, message = "The last name must be 60 characters or less")
    private String lastName;

    @Builder
    private PersonName(final String initials, final String firstName, final String lastName) {
        setInitials(initials);
        setFirstName(firstName);
        setLastName(lastName);
    }

    private void setInitials(final String initials) {
        this.initials = upperCase(deleteWhitespace(initials));
    }

    private void setFirstName(final String firstName) {
        this.firstName = capitalize(normalizeSpace(firstName));
    }

    public void setLastName(final String lastName) {
        this.lastName = capitalize(normalizeSpace(lastName));
    }
}
