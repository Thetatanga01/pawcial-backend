package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "animal_placement", schema = "pawcial")
class AnimalPlacement : BaseEntity() {

    companion object : PanacheCompanionBase<AnimalPlacement, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    var animal: Animal? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    var person: Person? = null

    @Column(name = "placement_type", nullable = false, length = 50)
    var placementType: String? = null

    @Column(nullable = false, length = 50)
    var status: String = "ACTIVE"

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate? = null

    @Column(name = "end_date")
    var endDate: LocalDate? = null

    @Column(name = "expected_end_date")
    var expectedEndDate: LocalDate? = null

    @Column(name = "placement_fee", precision = 12, scale = 2)
    var placementFee: BigDecimal? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intake_event_id")
    var intakeEvent: AnimalEvent? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_event_id")
    var outcomeEvent: AnimalEvent? = null

    @OneToMany(mappedBy = "placement", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var observations: MutableList<AnimalObservation> = mutableListOf()
}