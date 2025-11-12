package com.pawcial.service

import com.pawcial.dto.CreateVolunteerRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateVolunteerRequest
import com.pawcial.dto.VolunteerAreaRequest
import com.pawcial.dto.VolunteerDto
import com.pawcial.entity.core.Person
import com.pawcial.entity.core.Volunteer
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class VolunteerService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    @Inject
    lateinit var entityManager: jakarta.persistence.EntityManager

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<VolunteerDto> {
        val query = if (all) "1=1" else "isActive = true"

        // Get total count
        val totalElements = Volunteer.count(query)

        // Get paginated results with JOIN FETCH
        val content = if (all) {
            Volunteer.find(
                "SELECT DISTINCT v FROM Volunteer v " +
                "LEFT JOIN FETCH v.person " +
                "LEFT JOIN FETCH v.areas " +
                "WHERE 1=1 " +
                "ORDER BY v.createdAt DESC"
            ).page(page, size).list().map { it.toDto() }
        } else {
            Volunteer.find(
                "SELECT DISTINCT v FROM Volunteer v " +
                "LEFT JOIN FETCH v.person " +
                "LEFT JOIN FETCH v.areas " +
                "WHERE v.isActive = true " +
                "ORDER BY v.createdAt DESC"
            ).page(page, size).list().map { it.toDto() }
        }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
    }

    fun search(personName: String?, status: String?, volunteerCode: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<VolunteerDto> {
        var query = "SELECT DISTINCT v FROM Volunteer v " +
                "LEFT JOIN FETCH v.person p " +
                "LEFT JOIN FETCH v.areas " +
                "WHERE " + (if (all) "1=1" else "v.isActive = true")

        val params = mutableMapOf<String, Any>()

        if (!personName.isNullOrBlank()) {
            query += " and lower(p.fullName) like lower(:personName)"
            params["personName"] = "%$personName%"
        }

        if (!status.isNullOrBlank()) {
            query += " and lower(v.status) like lower(:status)"
            params["status"] = "%$status%"
        }

        if (!volunteerCode.isNullOrBlank()) {
            query += " and lower(v.volunteerCode) like lower(:volunteerCode)"
            params["volunteerCode"] = "%$volunteerCode%"
        }

        query += " ORDER BY v.createdAt DESC"

        // Get total count (without JOIN FETCH for count query)
        val countQuery = "SELECT COUNT(DISTINCT v) FROM Volunteer v " +
                "LEFT JOIN v.person p " +
                "WHERE " + (if (all) "1=1" else "v.isActive = true") +
                (if (!personName.isNullOrBlank()) " and lower(p.fullName) like lower(:personName)" else "") +
                (if (!status.isNullOrBlank()) " and lower(v.status) like lower(:status)" else "") +
                (if (!volunteerCode.isNullOrBlank()) " and lower(v.volunteerCode) like lower(:volunteerCode)" else "")

        val totalElements = Volunteer.find(countQuery, params).count()

        // Get paginated results with JOIN FETCH
        val content = Volunteer.find(query, params)
            .page(page, size)
            .list()
            .map { it.toDto() }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
    }

    fun findById(id: UUID): VolunteerDto {
        val volunteer = Volunteer.find(
            "SELECT v FROM Volunteer v " +
            "LEFT JOIN FETCH v.person " +
            "LEFT JOIN FETCH v.areas " +
            "WHERE v.id = :id",
            mapOf("id" to id)
        ).firstResult()

        return volunteer?.toDto()
            ?: throw NotFoundException("Volunteer not found: $id")
    }

    @Transactional
    fun create(request: CreateVolunteerRequest): VolunteerDto {
        val person = Person.findById(request.personId)
            ?: throw NotFoundException("Person not found: ${request.personId}")

        val volunteer = Volunteer().apply {
            this.person = person
            status = request.status.takeIf { it.isNotBlank() } ?: "ACTIVE"
            startDate = request.startDate
            endDate = request.endDate
            volunteerCode = request.volunteerCode?.takeIf { it.isNotBlank() }
            notes = request.notes?.takeIf { it.isNotBlank() }
        }
        volunteer.persist()

        // Save volunteer areas if provided
        request.areas?.forEach { areaRequest ->
            val volunteerArea = com.pawcial.entity.core.VolunteerArea().apply {
                this.volunteer = volunteer
                this.areaCode = areaRequest.areaCode
                this.proficiencyLevel = areaRequest.proficiencyLevel
                this.notes = areaRequest.notes
            }
            volunteerArea.persist()
            volunteer.areas.add(volunteerArea)
        }

        // Reload with JOIN FETCH to get all data properly
        return findById(volunteer.id!!)
    }

    @Transactional
    fun update(id: UUID, request: UpdateVolunteerRequest): VolunteerDto {
        // Load volunteer with JOIN FETCH
        val volunteer = Volunteer.find(
            "SELECT v FROM Volunteer v " +
            "LEFT JOIN FETCH v.person " +
            "LEFT JOIN FETCH v.areas " +
            "WHERE v.id = :id",
            mapOf("id" to id)
        ).firstResult() ?: throw NotFoundException("Volunteer not found: $id")

        request.personId?.let {
            volunteer.person = Person.findById(it)
                ?: throw NotFoundException("Person not found: $it")
        }

        volunteer.apply {
            request.status?.let { status = it.takeIf { it.isNotBlank() } ?: status }
            request.startDate?.let { startDate = it }
            request.endDate?.let { endDate = it }
            request.volunteerCode?.let { volunteerCode = it.takeIf { it.isNotBlank() } }
            request.notes?.let { notes = it.takeIf { it.isNotBlank() } }
        }

        // Update volunteer areas if provided
        request.areas?.let { newAreas ->
            // Delete all existing areas using bulk delete
            com.pawcial.entity.core.VolunteerArea.delete("volunteer.id = ?1", volunteer.id as Any)
            volunteer.areas.clear()

            // Flush and clear the persistence context to avoid conflicts
            entityManager.flush()
            entityManager.clear()

            // Reload volunteer after clearing session
            val reloadedVolunteer = Volunteer.findById(volunteer.id!!)
                ?: throw NotFoundException("Volunteer not found after clear: ${volunteer.id}")

            // Add new areas
            newAreas.forEach { areaRequest ->
                val volunteerArea = com.pawcial.entity.core.VolunteerArea().apply {
                    this.volunteer = reloadedVolunteer
                    this.areaCode = areaRequest.areaCode
                    this.proficiencyLevel = areaRequest.proficiencyLevel
                    this.notes = areaRequest.notes
                }
                volunteerArea.persist()
                reloadedVolunteer.areas.add(volunteerArea)
            }

            reloadedVolunteer.persist()

            // Reload with JOIN FETCH to get updated data
            return findById(reloadedVolunteer.id!!)
        }

        volunteer.persist()

        // Reload with JOIN FETCH to get updated data
        return findById(id)
    }

    @Transactional
    fun delete(id: UUID) {
        val volunteer = Volunteer.findById(id)
            ?: throw NotFoundException("Volunteer not found: $id")
        volunteer.isActive = !volunteer.isActive
        volunteer.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        // Load volunteer with JOIN FETCH to avoid lazy loading issues
        val volunteer = Volunteer.find(
            "SELECT v FROM Volunteer v " +
            "LEFT JOIN FETCH v.person " +
            "LEFT JOIN FETCH v.areas " +
            "LEFT JOIN FETCH v.activities " +
            "WHERE v.id = :id",
            mapOf("id" to id)
        ).firstResult() ?: throw NotFoundException("Volunteer not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = volunteer.createdAt ?: throw IllegalStateException("Volunteer has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        volunteer.delete()
    }
}
