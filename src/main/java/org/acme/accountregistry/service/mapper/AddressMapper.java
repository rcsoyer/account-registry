package org.acme.accountregistry.service.mapper;

import org.acme.accountregistry.domain.Address;
import org.acme.accountregistry.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toEntity(AddressDto dto);
}
