package org.acme.accountregistry.domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

/**
 * Specialized Identity Auditable root supper class.
 * <br/> It doesn't provide a generation strategy for the id field thus allowing developers to choose the most
 * appropriate one.
 */
@Getter
@ToString
@MappedSuperclass
abstract class AbstractIdEntity extends BaseAuditEntity {

    @Id
    private Long id;
}
