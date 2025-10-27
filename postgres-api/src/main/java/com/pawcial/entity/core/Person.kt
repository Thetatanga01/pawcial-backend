package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import com.pawcial.entity.dictionary.Organization
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "person", schema = "pawcial")
class Person : BaseEntity() {

    companion object : PanacheCompanionBase<Person, UUID>

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_code", referencedColumnName = "code")
    var organization: Organization? = null

    @OneToMany(mappedBy = "person", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var volunteers: MutableList<Volunteer> = mutableListOf()
}