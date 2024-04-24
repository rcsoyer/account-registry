package org.acme.accountregistry.service.mapper;

import org.acme.accountregistry.domain.Account;
import org.acme.accountregistry.domain.Principal;
import org.acme.accountregistry.service.dto.AccountRegisterRequest;
import org.acme.accountregistry.service.dto.AccountRegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PersonNameMapper.class, AddressMapper.class},
        imports = {Principal.class})
public interface AccountMapper {

    @Mapping(target = "principal",
             expression = "java(new Principal(request.username(), password))")
    Account toEntity(AccountRegisterRequest request, String password);

    @Mapping(target = "username", source = "account.principal.username")
    AccountRegisterResponse toResponse(Account account, String password);
}
