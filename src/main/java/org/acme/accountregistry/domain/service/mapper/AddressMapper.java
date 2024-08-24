package org.acme.accountregistry.domain.service.mapper;

import org.acme.accountregistry.domain.entity.Address;
import org.acme.accountregistry.domain.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toEntity(AddressDto dto);
}
