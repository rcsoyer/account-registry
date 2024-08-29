package org.acme.accountregistry.infrastructure.repository;

import org.acme.accountregistry.domain.entity.AccountAuthenticationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAuthenticationEventRepository
        extends JpaRepository<AccountAuthenticationEvent, Long> {}
