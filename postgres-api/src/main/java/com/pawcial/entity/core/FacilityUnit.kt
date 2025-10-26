package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "facility_unit", schema = "pawcial")
class FacilityUnit : BaseEntity() {

    companion object : PanacheCompanionBase<FacilityUnit, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    var facility: Facility? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    var zone: FacilityZone? = null

    @Column(nullable = false)
    var code: String? = null

    @Column(nullable = false, length = 50)
    var type: String? = null

    var capacity: Int? = null
}