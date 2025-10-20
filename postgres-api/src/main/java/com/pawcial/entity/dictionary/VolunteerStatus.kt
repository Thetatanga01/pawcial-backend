package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "dict_volunteer_status", schema = "pawcial")
class VolunteerStatus : PanacheEntityBase() {
@Id
    @Column(length = 50)
    var code: String? = null

    @Column(nullable = false)
    var label: String? = null
}
