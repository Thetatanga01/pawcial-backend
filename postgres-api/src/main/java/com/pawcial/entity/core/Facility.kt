package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "facility", schema = "pawcial")
class Facility : BaseEntity() {

    companion object : PanacheCompanionBase<Facility, UUID>

    @Column(nullable = false)
    var name: String? = null

    @Column(length = 50)
    var type: String? = null

    var country: String? = null

    var city: String? = null

    @Column(columnDefinition = "TEXT")
    var address: String? = null

    @OneToMany(mappedBy = "facility", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var zones: MutableList<FacilityZone> = mutableListOf()

    @OneToMany(mappedBy = "facility", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var units: MutableList<FacilityUnit> = mutableListOf()
}