package com.pawcial.extension

import com.pawcial.dto.*
import com.pawcial.entity.core.*
import com.pawcial.entity.dictionary.*

// Core entities extensions
fun Species.toDto() = SpeciesDto(
    id = this.id,
    scientificName = this.scientificName,
    commonName = this.commonName,
    domesticStatus = this.domesticStatus
)

fun Breed.toDto() = BreedDto(
    id = this.id,
    speciesId = this.species?.id,
    speciesName = this.species?.commonName,
    name = this.name,
    origin = this.origin
)

fun Facility.toDto() = FacilityDto(
    id = this.id,
    name = this.name,
    type = this.type,
    country = this.country,
    city = this.city,
    address = this.address
)

fun FacilityZone.toDto() = FacilityZoneDto(
    id = this.id,
    facilityId = this.facility?.id,
    facilityName = this.facility?.name,
    name = this.name,
    purpose = this.purpose
)

fun FacilityUnit.toDto() = FacilityUnitDto(
    id = this.id,
    facilityId = this.facility?.id,
    facilityName = this.facility?.name,
    zoneId = this.zone?.id,
    zoneName = this.zone?.name,
    code = this.code,
    type = this.type,
    capacity = this.capacity
)

fun Person.toDto() = PersonDto(
    id = this.id,
    fullName = this.fullName,
    phone = this.phone,
    email = this.email,
    address = this.address,
    notes = this.notes,
    isOrganization = this.isOrganization,
    organizationName = this.organizationName,
    organizationType = this.organizationType
)

fun Volunteer.toDto() = VolunteerDto(
    id = this.id,
    personId = this.person?.id,
    personName = this.person?.fullName,
    status = this.status,
    startDate = this.startDate,
    endDate = this.endDate,
    volunteerCode = this.volunteerCode,
    notes = this.notes
)

fun Asset.toDto() = AssetDto(
    id = this.id,
    facilityId = this.facility?.id,
    facilityName = this.facility?.name,
    unitId = this.unit?.id,
    unitCode = this.unit?.code,
    code = this.code,
    name = this.name,
    type = this.type,
    serialNo = this.serialNo,
    purchaseDate = this.purchaseDate,
    warrantyEnd = this.warrantyEnd,
    status = this.status
)

// Animal extension (existing)
fun Animal.toDto() = AnimalDto(
    id = this.id,
    speciesId = this.species?.id,
    speciesName = this.species?.commonName,
    breedId = this.breed?.id,
    breedName = this.breed?.name,
    name = this.name,
    sex = this.sex,
    birthDate = this.birthDate,
    ageMonthsEst = this.ageMonthsEst,
    size = this.size,
    color = this.color,
    trainingLevel = this.trainingLevel,
    sterilized = this.sterilized,
    isMixed = this.isMixed,
    originNote = this.originNote,
    currentUnitId = this.currentUnitId,
    currentSince = this.currentSince
)

// Dictionary entities extensions
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

