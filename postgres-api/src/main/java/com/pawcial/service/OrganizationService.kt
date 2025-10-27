package com.pawcial.service

import com.pawcial.dto.CreateOrganizationRequest
import com.pawcial.dto.OrganizationDto
import com.pawcial.dto.UpdateOrganizationRequest
import com.pawcial.entity.dictionary.Organization
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.time.LocalDateTime

@ApplicationScoped
class OrganizationService {

    fun findAll(all: Boolean = false): List<OrganizationDto> {
        return if (all) {
            Organization.findAll().list().map { it.toDto() }
        } else {
            Organization.find("isActive = true").list().map { it.toDto() }
        }
    }

    fun findById(code: String): OrganizationDto {
        return Organization.findById(code)?.toDto()
            ?: throw NotFoundException("Organization not found: $code")
    }

    @Transactional
    fun create(request: CreateOrganizationRequest): OrganizationDto {
        val existing = Organization.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Organization with code ${request.code} already exists")
        }

        val organization = Organization().apply {
            code = request.code
            label = request.label
            organizationType = request.organizationType
            contactPhone = request.contactPhone
            contactEmail = request.contactEmail
            address = request.address
            notes = request.notes
            isActive = true
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        }
        organization.persist()
        return organization.toDto()
    }

    @Transactional
    fun update(code: String, request: UpdateOrganizationRequest): OrganizationDto {
        val organization = Organization.findById(code)
            ?: throw NotFoundException("Organization not found: $code")

        organization.apply {
            request.label?.let { label = it }
            request.organizationType?.let { organizationType = it }
            request.contactPhone?.let { contactPhone = it }
            request.contactEmail?.let { contactEmail = it }
            request.address?.let { address = it }
            request.notes?.let { notes = it }
            updatedAt = LocalDateTime.now()
        }
        organization.persist()
        return organization.toDto()
    }

    @Transactional
    fun delete(code: String) {
        val organization = Organization.findById(code)
            ?: throw NotFoundException("Organization not found: $code")
        organization.isActive = !organization.isActive
        organization.updatedAt = LocalDateTime.now()
        organization.persist()
    }
}

