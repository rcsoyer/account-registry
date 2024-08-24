package org.acme.accountregistry.service.mapper;

import org.acme.accountregistry.domain.entity.PersonName;
import org.acme.accountregistry.service.dto.PersonNameDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonNameMapper {

    PersonName toEntity(PersonNameDto dto);
}
