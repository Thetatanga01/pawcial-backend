package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "animal_observation", schema = "pawcial")
class AnimalObservation : BaseEntity() {

    companion object : PanacheCompanionBase<AnimalObservation, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    var animal: Animal? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_id")
    var placement: AnimalPlacement? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    var person: Person? = null

    @Column(name = "observation_date", nullable = false)
    var observationDate: LocalDate = LocalDate.now()

    @Column(nullable = false, length = 50)
    var category: String? = null

    var title: String? = null

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String? = null

    var severity: String? = null

    @Column(name = "attachment_path")
    var attachmentPath: String? = null

    @Column(name = "requires_vet_attention")
    var requiresVetAttention: Boolean = false

    var resolved: Boolean = false

    @Column(name = "resolved_at")
    var resolvedAt: OffsetDateTime? = null

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    var resolutionNotes: String? = null
}