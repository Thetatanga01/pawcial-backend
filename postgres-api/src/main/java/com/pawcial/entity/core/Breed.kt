package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "breed", schema = "pawcial", uniqueConstraints = [
    UniqueConstraint(columnNames = ["species_id", "name"])
])
class Breed : BaseEntity() {
@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    var species: Species? = null

    @Column(nullable = false)
    var name: String? = null

    var origin: String? = null
}