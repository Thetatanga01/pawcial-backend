package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "animal_event", schema = "pawcial")
class AnimalEvent : BaseEntity() {

    companion object : PanacheCompanionBase<AnimalEvent, UUID>


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    var animal: Animal? = null

    @Column(name = "event_type", nullable = false, length = 50)
    var eventType: String? = null

    @Column(name = "event_at", nullable = false)
    var eventAt: OffsetDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    var facility: Facility? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    var unit: FacilityUnit? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_facility_id")
    var fromFacility: Facility? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_facility_id")
    var toFacility: Facility? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_unit_id")
    var fromUnit: FacilityUnit? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_unit_id")
    var toUnit: FacilityUnit? = null

    @Column(name = "outcome_type", length = 50)
    var outcomeType: String? = null

    @Column(name = "source_type", length = 50)
    var sourceType: String? = null

    @Column(name = "hold_type", length = 50)
    var holdType: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    var person: Person? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    var volunteer: Volunteer? = null

    @Column(name = "med_event_type", length = 50)
    var medEventType: String? = null

    @Column(name = "vaccine_code", length = 50)
    var vaccineCode: String? = null

    @Column(name = "medication_name")
    var medicationName: String? = null

    @Column(name = "dose_text")
    var doseText: String? = null

    @Column(length = 50)
    var route: String? = null

    @Column(name = "lab_test_name")
    var labTestName: String? = null

    @Column(name = "result_text", columnDefinition = "TEXT")
    var resultText: String? = null

    @Column(name = "next_due_date")
    var nextDueDate: LocalDate? = null

    @Column(name = "vet_name")
    var vetName: String? = null

    @Column(columnDefinition = "TEXT")
    var details: String? = null
}