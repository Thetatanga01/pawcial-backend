package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "dict_system_parameter", schema = "pawcial")
class SystemParameter : PanacheEntityBase {

    companion object : io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase<SystemParameter, String>

    @Id
    @Column(nullable = false)
    var code: String? = null

    @Column(nullable = false)
    var label: String? = null

    @Column(name = "parameter_value", nullable = false)
    var parameterValue: String? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true
}

