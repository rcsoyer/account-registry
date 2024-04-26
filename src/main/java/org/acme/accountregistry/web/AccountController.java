package org.acme.accountregistry.web;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.repository.projection.AccountOverview;
import org.acme.accountregistry.service.AccountService;
import org.acme.accountregistry.service.dto.AccountRegisterRequest;
import org.acme.accountregistry.service.dto.AccountRegisterResponse;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(CREATED)
    AccountRegisterResponse register(@RequestBody @Valid final AccountRegisterRequest request) {
        log.debug("Rest API call to register a new Account");
        return service.openAccount(request);
    }

    @GetMapping
    List<AccountOverview> getAccountOverview(final Authentication authentication) {
        log.debug("Rest API call to get the Account Overview");
        return service.getAccountOverview(authentication);
    }
}
