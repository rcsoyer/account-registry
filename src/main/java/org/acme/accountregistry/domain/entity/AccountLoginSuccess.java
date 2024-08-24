package org.acme.accountregistry.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountLoginSuccess extends AbstractImmutableEntity {}
