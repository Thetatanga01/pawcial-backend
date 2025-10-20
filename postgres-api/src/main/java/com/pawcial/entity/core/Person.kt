package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "person", schema = "pawcial")
class Person : BaseEntity() {
@Column(name = "full_name", nullable = false)
    var fullName: String? = null

    var phone: String? = null

    var email: String? = null

    @Column(columnDefinition = "TEXT")
    var address: String? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @Column(name = "is_organization")
    var isOrganization: Boolean = false

    @Column(name = "organization_name")
    var organizationName: String? = null

    @Column(name = "organization_type")
    var organizationType: String? = null

    @OneToMany(mappedBy = "person", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var volunteers: MutableList<Volunteer> = mutableListOf()
}