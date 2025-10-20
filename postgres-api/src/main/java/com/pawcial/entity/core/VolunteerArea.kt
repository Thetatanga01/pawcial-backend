package com.pawcial.entity.core

import com.pawcial.entity.core.data.VolunteerAreaId
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "volunteer_area", schema = "pawcial")
@IdClass(VolunteerAreaId::class)
class VolunteerArea : PanacheEntityBase() {
@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    var volunteer: Volunteer? = null

    @Id
    @Column(name = "area_code", nullable = false, length = 50)
    var areaCode: String? = null

    @Column(name = "proficiency_level")
    var proficiencyLevel: String? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null
}