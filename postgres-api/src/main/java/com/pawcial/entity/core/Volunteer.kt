package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "volunteer", schema = "pawcial")
class Volunteer : BaseEntity() {

    companion object : PanacheCompanionBase<Volunteer, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    var person: Person? = null

    @Column(nullable = false, length = 50)
    var status: String = "ACTIVE"

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate? = null

    @Column(name = "end_date")
    var endDate: LocalDate? = null

    @Column(name = "volunteer_code")
    var volunteerCode: String? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var areas: MutableList<VolunteerArea> = mutableListOf()

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var activities: MutableList<VolunteerActivity> = mutableListOf()
}