package org.acme.accountregistry.domain.service;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.dto.command.SendMoneyRequest;
import org.acme.accountregistry.domain.dto.command.TopUpMoneyRequest;
import org.acme.accountregistry.domain.entity.BankAccount;
import org.acme.accountregistry.domain.entity.Money;
import org.acme.accountregistry.domain.entity.TransferAccount;
import org.acme.accountregistry.infrastructure.repository.AccountTransactionTransferRepository;
import org.acme.accountregistry.infrastructure.repository.BankAccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.acme.accountregistry.domain.entity.AccountTransactionTransfer.moneyIn;
import static org.acme.accountregistry.domain.entity.AccountTransactionTransfer.moneyOut;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountTransactionTransferService {

    private final AccountTransactionTransferRepository repository;
    private final BankAccountRepository bankAccountRepository;

    public void topUp(final TopUpMoneyRequest request, final Authentication authentication) {
        log.debug("Topping up bank account with request command: {}", request);

        bankAccountRepository
          .findByIban(request.recipientIban())
          .ifPresentOrElse(recordMoneyIn(request, authentication), errorBankAccountNotFound(request));
    }

    private Consumer<BankAccount> recordMoneyIn(final TopUpMoneyRequest request,
                                                final Authentication authentication) {
        return bankAccount -> {
            final var money = new Money(request.amount(), request.currency());

            bankAccountRepository
              .fetchByIban(request.senderIban())
              .ifPresent(debitMoneyFromSenderAccount(authentication, bankAccount, money));

            final var transferAccount = new TransferAccount(request.senderIban(),
                                                            request.senderName());
            repository.save(moneyIn(bankAccount, transferAccount, money));
        };
    }

    private Consumer<BankAccount> debitMoneyFromSenderAccount(final Authentication authentication,
                                                              final BankAccount recipientAccount,
                                                              final Money money) {
        return senderAccount -> {
            final boolean isOwnerOfSenderAccount = senderAccount
                                                     .getAccountHolder()
                                                     .getUser()
                                                     .getUsername()
                                                     .equals(authentication.getName());
            if (isOwnerOfSenderAccount) {
                final var transferAccount =
                  new TransferAccount(recipientAccount.getIban(),
                                      recipientAccount.getAccountHolderName());
                repository.save(moneyOut(senderAccount, transferAccount, money));
            } else {
                log.warn("A User should not try to send money from an Account, in this system, that they are not the owner. "
                           + "userLoggedIn={}, senderAccountIban={}", authentication.getName(), senderAccount.getIban());
                throw new ResponseStatusException(FORBIDDEN,
                                                  "User not owner of the sender bank account");
            }
        };
    }

    public void sendMoney(final long senderBankAccountId,
                          final SendMoneyRequest request,
                          final Authentication authentication) {
        log.debug("Sending money request: {}", request);

        bankAccountRepository
          .findBy(senderBankAccountId, authentication.getName())
          .ifPresentOrElse(recordMoneyOut(request), errorBankAccountNotFound(request));
    }

    private Runnable errorBankAccountNotFound(final Object request) {
        return () -> {
            log.warn("This bank account don't exist in this system. "
                       + "Invalid data from the given payload: {}",
                     request);
            throw new ResponseStatusException(NOT_FOUND, "Bank account not found in this application");
        };
    }

    private Consumer<BankAccount> recordMoneyOut(final SendMoneyRequest request) {
        return senderAccount -> {
            final var money = new Money(request.amount(), request.currency());

            bankAccountRepository
              .findByIban(request.recipientIban())
              .ifPresent(recipientAccount -> {
                  final var transferAccount =
                    new TransferAccount(senderAccount.getIban(),
                                        senderAccount.getAccountHolderName());

                  repository.save(moneyIn(recipientAccount, transferAccount, money));
              });

            final var transferAccount = new TransferAccount(request.recipientIban(),
                                                            request.recipientName());
            repository.save(moneyOut(senderAccount, transferAccount, money));
        };
    }
}
