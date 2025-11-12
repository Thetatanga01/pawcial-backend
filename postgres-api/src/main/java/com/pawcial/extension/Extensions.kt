package com.pawcial.extension

import com.pawcial.dto.*
import com.pawcial.entity.core.*
import com.pawcial.entity.dictionary.*

// Dictionary entities extensions
fun SystemParameter.toDto() = SystemParameterDto(
    code = this.code,
    label = this.label,
    parameterValue = this.parameterValue,
    description = this.description,
    isActive = this.isActive
)

fun Organization.toDto() = OrganizationDto(
    code = this.code,
    label = this.label,
    organizationType = this.organizationType,
    contactPhone = this.contactPhone,
    contactEmail = this.contactEmail,
    address = this.address,
    notes = this.notes,
    isActive = this.isActive
)

// Core entities extensions
fun Species.toDto() = SpeciesDto(
    id = this.id,
    scientificName = this.scientificName,
    commonName = this.commonName,
    domesticStatus = this.domesticStatus,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Breed.toDto() = BreedDto(
    id = this.id,
    speciesId = this.species?.id,
    speciesName = this.species?.commonName,
    name = this.name,
    origin = this.origin,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Facility.toDto() = FacilityDto(
    id = this.id,
    name = this.name,
    type = this.type,
    country = this.country,
    city = this.city,
    address = this.address,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun FacilityZone.toDto() = FacilityZoneDto(
    id = this.id,
    facilityId = this.facility?.id,
    facilityName = this.facility?.name,
    name = this.name,
    purpose = this.purpose,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun FacilityUnit.toDto() = FacilityUnitDto(
    id = this.id,
    facilityId = this.facility?.id,
    facilityName = this.facility?.name,
    zoneId = this.zone?.id,
    zoneName = this.zone?.name,
    code = this.code,
    type = this.type,
    capacity = this.capacity,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Person.toDto() = PersonDto(
    id = this.id,
    fullName = this.fullName,
    phone = this.phone,
    email = this.email,
    address = this.address,
    notes = this.notes,
    isOrganization = this.isOrganization,
    organization = this.organization?.toDto(),
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Volunteer.toDto() = VolunteerDto(
    id = this.id,
    personId = this.person?.id,
    personName = this.person?.fullName,
    status = this.status,
    startDate = this.startDate,
    endDate = this.endDate,
    volunteerCode = this.volunteerCode,
    notes = this.notes,
    areas = this.areas.filter { it.isActive }.map { it.toDto() },
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun VolunteerArea.toDto() = VolunteerAreaDto(
    volunteerId = this.volunteer?.id,
    areaCode = this.areaCode,
    proficiencyLevel = this.proficiencyLevel,
    notes = this.notes,
    isActive = this.isActive,
    createdAt = this.createdAt
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
    status = this.status,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
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
    leashBehavior = this.leashBehavior,
    trainingLevel = this.trainingLevel,
    sterilized = this.sterilized,
    isMixed = this.isMixed,
    originNote = this.originNote,
    currentUnitId = this.currentUnitId,
    currentSince = this.currentSince,
    temperaments = this.temperaments.map { it.code ?: "" },
    healthFlags = this.healthFlags.map { it.code ?: "" },
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

// Dictionary entities extensions
fun Color.toDto() = ColorDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun AssetStatus.toDto() = AssetStatusDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun AssetType.toDto() = AssetTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun DomesticStatus.toDto() = DomesticStatusDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun DoseRoute.toDto() = DoseRouteDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun EventType.toDto() = EventTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun FacilityType.toDto() = FacilityTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun HealthFlag.toDto() = HealthFlagDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun HoldType.toDto() = HoldTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun MedEventType.toDto() = MedEventTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun ObservationCategory.toDto() = ObservationCategoryDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun OutcomeType.toDto() = OutcomeTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun PlacementStatus.toDto() = PlacementStatusDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun PlacementType.toDto() = PlacementTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun ServiceType.toDto() = ServiceTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun Sex.toDto() = SexDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun Size.toDto() = SizeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun SourceType.toDto() = SourceTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun Temperament.toDto() = TemperamentDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun TrainingLevel.toDto() = TrainingLevelDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun UnitType.toDto() = UnitTypeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun Vaccine.toDto() = VaccineDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun VolunteerAreaDictionary.toDto() = VolunteerAreaDictionaryDto(
    code = this.code,
    label = this.label,
    description = this.description,
    isActive = this.isActive
)

fun VolunteerStatus.toDto() = VolunteerStatusDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

fun ProficiencyLevel.toDto() = ProficiencyLevelDto(
    code = this.code!!,
    label = this.label!!,
    description = this.description,
    displayOrder = this.displayOrder,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun LeashBehavior.toDto() = LeashBehaviorDto(
    code = this.code!!,
    label = this.label!!,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun ZonePurpose.toDto() = ZonePurposeDto(
    code = this.code,
    label = this.label,
    isActive = this.isActive
)

