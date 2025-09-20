package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Auditable root supper class.
 * <br/> It provides the basic fields for JPA entities that are also auditable.
 * <br/> It uses {@link jakarta.persistence.GenerationType#IDENTITY} id field for JPA entities.
 */
@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractIdentityEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "date_created", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "date_modified")
    private Instant modifiedAt;

    /**
     * username that created the entity
     */
    @NotBlank
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    /**
     * username that last modified the entity
     */
    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

}
