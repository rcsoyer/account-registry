package org.acme.accountregistry.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountLoginFailure extends AbstractImmutableEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;
}
