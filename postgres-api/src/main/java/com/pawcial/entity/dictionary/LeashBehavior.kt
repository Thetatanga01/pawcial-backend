package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "dict_leash_behavior", schema = "pawcial")
class LeashBehavior : PanacheEntityBase {

    companion object : PanacheCompanionBase<LeashBehavior, String>

    @Id
    @Column(length = 50)
    var code: String? = null

    @Column(nullable = false, length = 100)
    var label: String? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = null
}

