package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "asset", schema = "pawcial", uniqueConstraints = [
    UniqueConstraint(columnNames = ["facility_id", "code"])
])
class Asset : BaseEntity() {
@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    var facility: Facility? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    var unit: FacilityUnit? = null

    @Column(nullable = false)
    var code: String? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(length = 50)
    var type: String? = null

    @Column(name = "serial_no")
    var serialNo: String? = null

    @Column(name = "purchase_date")
    var purchaseDate: LocalDate? = null

    @Column(name = "warranty_end")
    var warrantyEnd: LocalDate? = null

    @Column(length = 50)
    var status: String? = null

    @OneToMany(mappedBy = "asset", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var services: MutableList<AssetService> = mutableListOf()
}