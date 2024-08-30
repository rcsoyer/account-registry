package org.acme.accountregistry.domain.service.mapper;

import org.acme.accountregistry.domain.entity.Account;
import org.acme.accountregistry.domain.entity.AccountAuthenticationEvent;
import org.mapstruct.Mapper;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@Mapper(uses = {AccountMapper.class})
public interface AccountAuthenticationEventMapper {

    AccountAuthenticationEvent sucessAccountAuthenticationEvent(
            Account account, AuthenticationSuccessEvent event);
}
