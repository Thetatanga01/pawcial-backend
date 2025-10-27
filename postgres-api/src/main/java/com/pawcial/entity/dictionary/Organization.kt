package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "dict_organization", schema = "pawcial")
class Organization : PanacheEntityBase {

    companion object : PanacheCompanionBase<Organization, String>

    @Id
    @Column(length = 100)
    var code: String? = null

    @Column(nullable = false)
    var label: String? = null

    @Column(name = "organization_type")
    var organizationType: String? = null

    @Column(name = "contact_phone")
    var contactPhone: String? = null

    @Column(name = "contact_email")
    var contactEmail: String? = null

    @Column(columnDefinition = "TEXT")
    var address: String? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}

