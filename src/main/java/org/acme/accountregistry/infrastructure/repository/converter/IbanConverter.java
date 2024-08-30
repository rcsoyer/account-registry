package org.acme.accountregistry.infrastructure.repository.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.iban4j.Iban;

@Converter(autoApply = true)
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
