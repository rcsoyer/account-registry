package org.acme.accountregistry.domain.service.mapper;

import org.acme.accountregistry.domain.dto.command.AccountRegisterRequest;
import org.acme.accountregistry.domain.dto.command.AccountRegisterResponse;
import org.acme.accountregistry.domain.entity.Account;
import org.acme.accountregistry.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PersonNameMapper.class, AddressMapper.class},
  imports = {User.class})
public interface AccountMapper {

    @Mapping(target = "user", expression = "java(new User(request.username(), password))")
    Account toEntity(AccountRegisterRequest request, String password);

    @Mapping(target = "username", source = "account.user.username")
    AccountRegisterResponse toResponse(Account account, String password);
}
