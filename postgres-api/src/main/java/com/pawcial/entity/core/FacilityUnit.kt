package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "facility_unit", schema = "pawcial", uniqueConstraints = [
    UniqueConstraint(columnNames = ["facility_id", "code"])
])
class FacilityUnit : BaseEntity() {
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