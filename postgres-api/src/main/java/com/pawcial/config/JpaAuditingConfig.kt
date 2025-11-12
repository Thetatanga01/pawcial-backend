package com.pawcial.config

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
 * JPA Entity Listener for automatic timestamp management
 * Replaces @CreatedDate and @LastModifiedDate functionality in Quarkus
 */
class AuditingEntityListener {

    @PrePersist
    fun prePersist(entity: Any) {
        if (entity is Auditable) {
            val now = OffsetDateTime.now()
            entity.createdAt = now
            entity.updatedAt = now
        }
    }

    @PreUpdate
    fun preUpdate(entity: Any) {
        if (entity is Auditable) {
            entity.updatedAt = OffsetDateTime.now()
        }
    }
}

/**
 * Interface for entities that support auditing
 */
interface Auditable {
    var createdAt: OffsetDateTime?
    var updatedAt: OffsetDateTime?
}

