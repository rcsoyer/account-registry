package org.acme.accountregistry.infrastructure.repository.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import lombok.SneakyThrows;

import java.net.InetAddress;

@Converter(autoApply = true)
class InetAddressConverter implements AttributeConverter<InetAddress, String> {

    @Override
    public String convertToDatabaseColumn(final InetAddress remoteAddress) {
        return remoteAddress.toString();
    }

    @Override
    @SneakyThrows
    public InetAddress convertToEntityAttribute(final String remoteAddress) {
        return InetAddress.getByAddress(remoteAddress.getBytes());
    }
}
