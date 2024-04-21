package org.acme.accountregistry.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * A person's name.
 * <br/> A person name is usually defined by: initials, a first name and a last name.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PersonName {

    private String initials;

    @NotBlank(message = "The first name is mandatory")
    private String firstName;

    @NotBlank(message = "The last name is mandatory")
    private String lastName;

    @Builder
    private PersonName(final String initials, final String firstName, final String lastName) {
        this.initials = upperCase(deleteWhitespace(initials));
        this.firstName = capitalize(normalizeSpace(firstName));
        this.lastName = capitalize(normalizeSpace(lastName));
    }
}
