package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "facility_zone", schema = "pawcial")
class FacilityZone : BaseEntity() {

    companion object : PanacheCompanionBase<FacilityZone, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    var facility: Facility? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(length = 50)
    var purpose: String? = null

    @OneToMany(mappedBy = "zone", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var units: MutableList<FacilityUnit> = mutableListOf()
}