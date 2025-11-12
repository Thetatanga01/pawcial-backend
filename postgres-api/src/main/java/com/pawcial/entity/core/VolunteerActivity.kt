package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "volunteer_activity", schema = "pawcial")
class VolunteerActivity : BaseEntity() {

    companion object : PanacheCompanionBase<VolunteerActivity, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    var volunteer: Volunteer? = null

    @Column(name = "activity_date", nullable = false)
    var activityDate: LocalDate? = null

    @Column(name = "area_code", length = 50)
    var areaCode: String? = null

    @Column(precision = 5, scale = 2)
    var hours: BigDecimal? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null
}