package com.pawcial.extension

import com.pawcial.dto.AnimalDto
import com.pawcial.dto.ColorDto
import com.pawcial.entity.core.Animal
import com.pawcial.entity.dictionary.Color

// Extension function
fun Animal.toDto() = AnimalDto(
    id = this.id,
    name = this.name,
    sex = this.sex,
    speciesName = this.species?.commonName,
    breedName = this.breed?.name
)

fun Color.toDto() = ColorDto(
    code = this.code,
    label = this.label
)
