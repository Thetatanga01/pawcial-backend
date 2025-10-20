package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "facility", schema = "pawcial")
class Facility : BaseEntity() {
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