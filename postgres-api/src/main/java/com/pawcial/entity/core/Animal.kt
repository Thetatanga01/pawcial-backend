package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import com.pawcial.entity.dictionary.HealthFlag
import com.pawcial.entity.dictionary.Temperament
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "animal", schema = "pawcial")
class Animal : BaseEntity() {

    companion object : PanacheCompanionBase<Animal, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    var species: Species? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    var breed: Breed? = null

    var name: String? = null

    @Column(length = 50)
    var sex: String? = null

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null

    @Column(name = "age_months_est")
    var ageMonthsEst: Int? = null

    @Column(length = 50)
    var size: String? = null

    @Column(length = 50)
    var color: String? = null

    @Column(name = "leash_behavior", length = 50)
    var leashBehavior: String? = null

    @Column(name = "training_level", length = 50)
    var trainingLevel: String? = null

    var sterilized: Boolean? = null

    @Column(name = "is_mixed")
    var isMixed: Boolean = false

    @Column(name = "origin_note", columnDefinition = "TEXT")
    var originNote: String? = null

    @Column(name = "current_unit_id", columnDefinition = "uuid")
    var currentUnitId: UUID? = null

    @Column(name = "current_since")
    var currentSince: OffsetDateTime? = null

    @OneToMany(mappedBy = "animal", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var breedCompositions: MutableList<AnimalBreedComposition> = mutableListOf()

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(
        name = "animal_temperament",
        schema = "pawcial",
        joinColumns = [JoinColumn(name = "animal_id")],
        inverseJoinColumns = [JoinColumn(name = "temperament_code")]
    )
    var temperaments: MutableSet<Temperament> = mutableSetOf()

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(
        name = "animal_health_flag",
        schema = "pawcial",
        joinColumns = [JoinColumn(name = "animal_id")],
        inverseJoinColumns = [JoinColumn(name = "health_code")]
    )
    var healthFlags: MutableSet<HealthFlag> = mutableSetOf()

    @OneToMany(mappedBy = "animal", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var events: MutableList<AnimalEvent> = mutableListOf()

    @OneToMany(mappedBy = "animal", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var placements: MutableList<AnimalPlacement> = mutableListOf()

    @OneToMany(mappedBy = "animal", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var observations: MutableList<AnimalObservation> = mutableListOf()
}