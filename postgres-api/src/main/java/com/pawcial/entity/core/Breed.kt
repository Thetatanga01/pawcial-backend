package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "breed", schema = "pawcial")
class Breed : BaseEntity() {

    companion object : PanacheCompanionBase<Breed, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    var species: Species? = null

    @Column(nullable = false)
    var name: String? = null

    var origin: String? = null
}