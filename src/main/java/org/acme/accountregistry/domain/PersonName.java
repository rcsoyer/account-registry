package org.acme.accountregistry.domain;

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
import static org.apache.commons.text.WordUtils.capitalizeFully;

/**
 * A person's name.
 * <br/> A person name is usually defined by: initials, a first name and a last name.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PersonName {

    @Size(max = 10)
    @NotBlank(message = "The name initials are mandatory")
    private String initials;

    @Size(max = 60)
    @NotBlank(message = "The first name is mandatory")
    private String firstName;

    @Size(max = 60)
    @NotBlank(message = "The last name is mandatory")
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
        this.firstName = capitalizeFully(normalizeSpace(firstName));
    }

    public void setLastName(final String lastName) {
        this.lastName = capitalizeFully(normalizeSpace(lastName));
    }
}
