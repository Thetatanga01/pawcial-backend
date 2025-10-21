package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "dict_med_event_type", schema = "pawcial")
class MedEventType : PanacheEntityBase {

    companion object : PanacheCompanionBase<MedEventType, String>

    @Id
    @Column(length = 50)
    var code: String? = null

    @Column(nullable = false)
    var label: String? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true
}