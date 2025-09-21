package org.acme.accountregistry.domain.service;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.dto.command.SendMoneyRequest;
import org.acme.accountregistry.domain.dto.command.TopUpMoneyRequest;
import org.acme.accountregistry.domain.entity.AccountTransactionTransfer;
import org.acme.accountregistry.domain.entity.BankAccount;
import org.acme.accountregistry.domain.entity.Money;
import org.acme.accountregistry.domain.entity.TransferAccount;
import org.acme.accountregistry.infrastructure.repository.AccountTransactionTransferRepository;
import org.acme.accountregistry.infrastructure.repository.BankAccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountTransactionTransferService {

    private final AccountTransactionTransferRepository repository;
    private final BankAccountRepository bankAccountRepository;

    public void topUp(final TopUpMoneyRequest request) {
        log.debug("Topping up bank account with request command: {}", request);

        bankAccountRepository
          .findByIban(request.recipientIban())
          .ifPresentOrElse(recordMoneyIn(request), errorBankAccountNotFound(request));
    }

    private Consumer<BankAccount> recordMoneyIn(final TopUpMoneyRequest request) {
        return bankAccount -> {
            final var transferAccount = new TransferAccount(request.senderIban(),
                                                            request.senderName());
            final var money = new Money(request.amount(), request.currency());
            final var moneyIn =
              AccountTransactionTransfer.moneyIn(bankAccount, transferAccount, money);
            repository.save(moneyIn);
        };
    }

    public void sendMoney(final SendMoneyRequest request, final Authentication authentication) {
        log.debug("Sending money request: {}", request);

        bankAccountRepository
          .findBy(request.senderBankAccountId(), authentication.getName())
          .ifPresentOrElse(recordMoneyOut(request), errorBankAccountNotFound(request));
    }

    private Runnable errorBankAccountNotFound(final Object request) {
        return () -> {
            log.warn("Sender bank account not found. Invalid data from the given payload: {}",
                     request);
            throw new ResponseStatusException(NOT_FOUND, "Sender bank account not found");
        };
    }

    private Consumer<BankAccount> recordMoneyOut(final SendMoneyRequest request) {
        return bankAccount -> {
            final var transferAccount = new TransferAccount(request.recipientIban(),
                                                            request.recipientName());
            final var money = new Money(request.amount(), request.currency());
            final var moneyOut =
              AccountTransactionTransfer.moneyOut(bankAccount, transferAccount, money);
            repository.save(moneyOut);
        };
    }
}
