package com.pawcial.extension

import com.pawcial.dto.*
import com.pawcial.entity.core.Animal
import com.pawcial.entity.dictionary.*

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

fun AssetStatus.toDto() = AssetStatusDto(
    code = this.code,
    label = this.label
)

fun AssetType.toDto() = AssetTypeDto(
    code = this.code,
    label = this.label
)

fun DomesticStatus.toDto() = DomesticStatusDto(
    code = this.code,
    label = this.label
)

fun DoseRoute.toDto() = DoseRouteDto(
    code = this.code,
    label = this.label
)

fun EventType.toDto() = EventTypeDto(
    code = this.code,
    label = this.label
)

fun FacilityType.toDto() = FacilityTypeDto(
    code = this.code,
    label = this.label
)

fun HealthFlag.toDto() = HealthFlagDto(
    code = this.code,
    label = this.label
)

fun HoldType.toDto() = HoldTypeDto(
    code = this.code,
    label = this.label
)

fun MedEventType.toDto() = MedEventTypeDto(
    code = this.code,
    label = this.label
)

fun ObservationCategory.toDto() = ObservationCategoryDto(
    code = this.code,
    label = this.label
)

fun OutcomeType.toDto() = OutcomeTypeDto(
    code = this.code,
    label = this.label
)

fun PlacementStatus.toDto() = PlacementStatusDto(
    code = this.code,
    label = this.label
)

fun PlacementType.toDto() = PlacementTypeDto(
    code = this.code,
    label = this.label
)

fun ServiceType.toDto() = ServiceTypeDto(
    code = this.code,
    label = this.label
)

fun Sex.toDto() = SexDto(
    code = this.code,
    label = this.label
)

fun Size.toDto() = SizeDto(
    code = this.code,
    label = this.label
)

fun SourceType.toDto() = SourceTypeDto(
    code = this.code,
    label = this.label
)

fun Temperament.toDto() = TemperamentDto(
    code = this.code,
    label = this.label
)

fun TrainingLevel.toDto() = TrainingLevelDto(
    code = this.code,
    label = this.label
)

fun UnitType.toDto() = UnitTypeDto(
    code = this.code,
    label = this.label
)

fun Vaccine.toDto() = VaccineDto(
    code = this.code,
    label = this.label
)

fun VolunteerAreaDictionary.toDto() = VolunteerAreaDto(
    code = this.code,
    label = this.label,
    description = this.description
)

fun VolunteerStatus.toDto() = VolunteerStatusDto(
    code = this.code,
    label = this.label
)

fun ZonePurpose.toDto() = ZonePurposeDto(
    code = this.code,
    label = this.label
)

