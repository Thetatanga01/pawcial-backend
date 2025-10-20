package com.pawcial.entity.dictionary

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "dict_asset_status", schema = "pawcial")
class AssetStatus : PanacheEntityBase {

    companion object : PanacheCompanionBase<AssetStatus, String>

    @Id
    @Column(length = 50)
    var code: String? = null

    @Column(nullable = false)
    var label: String? = null
}
