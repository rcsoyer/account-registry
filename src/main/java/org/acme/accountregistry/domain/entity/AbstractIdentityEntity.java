package org.acme.accountregistry.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Specialized Identity Auditable root supper class.
 * <br/> It provides {@link jakarta.persistence.GenerationType#IDENTITY} id field for JPA entities.
 */
@Getter
@ToString
@MappedSuperclass
abstract class AbstractIdentityEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

}
