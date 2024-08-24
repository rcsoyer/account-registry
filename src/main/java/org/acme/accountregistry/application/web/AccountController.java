package org.acme.accountregistry.application.web;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.infrastructure.repository.projection.AccountOverview;
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
import org.zalando.problem.Problem;

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
    @Operation(summary = "Register a new account",
               description = "This operation registers a new unique client's Account into the system. " +
                               "<br/> This automatically creates a new Payments Bank Account with a default balance ")
    @ApiResponse(responseCode = "201", description = "Account successfully registered")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
                 content = @Content(mediaType = "application/problem+json",
                                    schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "409", description = "Account username already exists",
                 content = @Content(mediaType = "application/problem+json",
                                    schema = @Schema(implementation = Problem.class)))
    AccountRegisterResponse register(@RequestBody @Valid final AccountRegisterRequest request) {
        log.debug("Rest API call to register a new Account");
        return service.openAccount(request);
    }

    @GetMapping
    @Operation(summary = "Get Account Overview of all banking and financial client's data",
               description = "If the user is authenticated, this operation returns the Account Overview of all banking and financial client's data")
    @ApiResponse(responseCode = "200", description = "User is authenticated and Account Overview is returned")
    @ApiResponse(responseCode = "401", description = "User is not authenticated. Client must be authenticated to access this resource",
                 content = @Content(mediaType = "application/problem+json",
                                    schema = @Schema(implementation = Problem.class)))
    List<AccountOverview> getAccountOverview(final Authentication authentication) {
        log.debug("Rest API call to get the Account Overview");
        return service.getAccountOverview(authentication);
    }
}
