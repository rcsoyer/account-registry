package org.acme.accountregistry.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@ToString
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractImmutableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "date_created", updatable = false)
    private Instant createdAt;

    /** username that created the entity */
    @NotBlank
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;
}
