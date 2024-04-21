package org.acme.accountregistry.domain;

import jakarta.persistence.AttributeConverter;
import org.iban4j.Iban;

class IbanConverter implements AttributeConverter<Iban, String> {

    @Override
    public String convertToDatabaseColumn(final Iban iban) {
        return iban.toString();
    }

    @Override
    public Iban convertToEntityAttribute(final String iban) {
        return Iban.valueOf(iban);
    }
}
