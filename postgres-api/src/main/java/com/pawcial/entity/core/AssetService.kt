package com.pawcial.entity.core

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "asset_service", schema = "pawcial")
class AssetService : PanacheEntityBase() {
@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    var asset: Asset? = null

    @Column(name = "service_at", nullable = false)
    var serviceAt: OffsetDateTime? = null

    @Column(name = "service_type", length = 50)
    var serviceType: String? = null

    var vendor: String? = null

    @Column(precision = 12, scale = 2)
    var cost: BigDecimal? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null
}