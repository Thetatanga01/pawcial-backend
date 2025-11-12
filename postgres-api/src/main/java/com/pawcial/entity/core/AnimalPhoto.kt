package com.pawcial.entity.core

import com.pawcial.entity.BaseEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "animal_photo", schema = "pawcial")
class AnimalPhoto : BaseEntity() {

    companion object : PanacheCompanionBase<AnimalPhoto, UUID>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    var animal: Animal? = null

    @Column(name = "photo_url", nullable = false)
    var photoUrl: String? = null

    @Column(name = "s3_key", nullable = false)
    var s3Key: String? = null

    @Column(name = "photo_order")
    var photoOrder: Int? = null

    @Column(name = "is_primary")
    var isPrimary: Boolean = false

    @Column(length = 500)
    var description: String? = null
}

