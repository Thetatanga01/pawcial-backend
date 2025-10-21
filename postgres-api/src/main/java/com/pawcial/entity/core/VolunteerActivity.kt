package com.pawcial.entity.core

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "volunteer_activity", schema = "pawcial")
class VolunteerActivity : PanacheEntityBase {

    companion object : PanacheCompanionBase<VolunteerActivity, UUID>

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    var id: UUID? = null

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null
}