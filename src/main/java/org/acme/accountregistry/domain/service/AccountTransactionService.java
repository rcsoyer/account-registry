package org.acme.accountregistry.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.dto.query.AccountTransactionOverview;
import org.acme.accountregistry.infrastructure.repository.AccountTransactionTransferRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountTransactionService {

    private final AccountTransactionTransferRepository accountTransactionTransferRepository;

    public Slice<AccountTransactionOverview> getAccountTransactions(
      final long bankAccountId, final Authentication authentication, final Pageable pageable) {
        return accountTransactionTransferRepository
                 .findTransactions(bankAccountId, authentication.getName(), pageable);
    }
}
