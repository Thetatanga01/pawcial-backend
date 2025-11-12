package com.pawcial.entity.core

import com.pawcial.entity.core.data.AnimalBreedCompositionId
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.OffsetDateTime


@Entity
@Table(name = "animal_breed_composition", schema = "pawcial")
@IdClass(AnimalBreedCompositionId::class)
class AnimalBreedComposition : PanacheEntityBase {

    companion object : PanacheCompanionBase<AnimalBreedComposition, AnimalBreedCompositionId>

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    var animal: Animal? = null

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    var breed: Breed? = null

    @Column(precision = 5, scale = 2)
    var percentage: BigDecimal? = null

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true
}