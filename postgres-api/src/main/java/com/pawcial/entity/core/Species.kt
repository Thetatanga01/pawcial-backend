package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "species", schema = "pawcial")
class Species : BaseEntity() {
@Column(name = "scientific_name", nullable = false)
    var scientificName: String? = null

    @Column(name = "common_name")
    var commonName: String? = null

    @Column(name = "domestic_status", length = 50)
    var domesticStatus: String? = null

    @OneToMany(mappedBy = "species", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var breeds: MutableList<Breed> = mutableListOf()
}