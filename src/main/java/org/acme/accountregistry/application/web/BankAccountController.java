package org.acme.accountregistry.application.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.dto.command.SendMoneyRequest;
import org.acme.accountregistry.domain.dto.command.TopUpMoneyRequest;
import org.acme.accountregistry.domain.service.AccountTransactionTransferService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("bank-accounts")
class BankAccountController {

    private final AccountTransactionTransferService service;

    @PostMapping("{bank-account-id}/send-funds")
    @ResponseStatus(CREATED)
    @Operation(summary = "Transfer funds, send money from a bank account in this system to another bank account",
      description = "A User only can send money from their own Bank Account",
      parameters = @Parameter(name = "bank-account-id",
        description = "ID of the bank account from which the funds will be taken from",
        in = ParameterIn.PATH, required = true))
    @ApiResponse(responseCode = "201", description = "Funds successfully transferred")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
      content = @Content(mediaType = "application/problem+json",
        schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "404", description = "Unknown Bank Account",
      content = @Content(mediaType = "application/problem+json",
        schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "409", description = "Insufficient funds in Bank Account to execute the transfer",
      content = @Content(mediaType = "application/problem+json",
        schema = @Schema(implementation = Problem.class)))
    void sendMoney(@RequestBody @Valid final SendMoneyRequest request,
                   final Authentication authentication) {
        log.debug("Rest API call to send money from one bank account to another");
        service.sendMoney(request, authentication);
    }

    @PostMapping("topup")
    @ResponseStatus(CREATED)
    @Operation(summary = "Topup money into a bank account in this system")
    @ApiResponse(responseCode = "201", description = "Funds successfully transferred")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
      content = @Content(mediaType = "application/problem+json",
        schema = @Schema(implementation = Problem.class)))
    @ApiResponse(responseCode = "404", description = "Unknown Bank Account",
      content = @Content(mediaType = "application/problem+json",
        schema = @Schema(implementation = Problem.class)))
    void topUpMoney(@RequestBody @Valid final TopUpMoneyRequest request) {
        log.debug("Rest API call to topup money to an account in this application");
        service.topUp(request);
    }
}
