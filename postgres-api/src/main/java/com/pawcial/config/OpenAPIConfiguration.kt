package com.pawcial.config

import jakarta.ws.rs.core.Application
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.servers.Server
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@OpenAPIDefinition(
    info = Info(
        title = "Pawcial API",
        version = "1.0.0",
        description = "Pawcial - Hayvan Barınağı Yönetim Sistemi REST API. " +
                "Bu API, hayvan barınağı yönetimi için gerekli tüm işlemleri sağlar.",
        contact = Contact(
            name = "Pawcial Team",
            email = "info@pawcial.com"
        )
    ),
    servers = [
        Server(url = "http://localhost:8000", description = "Development Server")
    ],
    tags = [
        Tag(name = "Colors", description = "Renk (Color) yönetimi"),
        Tag(name = "Domestic Statuses", description = "Evcilleştirme Durumu yönetimi"),
        Tag(name = "Training Levels", description = "Eğitim Seviyesi yönetimi"),
        Tag(name = "Placement Types", description = "Yerleştirme Tipi yönetimi"),
        Tag(name = "Placement Statuses", description = "Yerleştirme Durumu yönetimi"),
        Tag(name = "Sizes", description = "Boyut yönetimi"),
        Tag(name = "Med Event Types", description = "Tıbbi Olay Tipi yönetimi"),
        Tag(name = "Service Types", description = "Hizmet Tipi yönetimi"),
        Tag(name = "Event Types", description = "Olay Tipi yönetimi"),
        Tag(name = "Health Flags", description = "Sağlık İşareti yönetimi"),
        Tag(name = "Facility Types", description = "Tesis Tipi yönetimi"),
        Tag(name = "Temperaments", description = "Mizaç yönetimi"),
        Tag(name = "Volunteer Areas", description = "Gönüllü Alanı yönetimi"),
        Tag(name = "Animals", description = "Hayvan yönetimi"),
        Tag(name = "Persons", description = "Kişi yönetimi"),
        Tag(name = "Volunteers", description = "Gönüllü yönetimi"),
        Tag(name = "Assets", description = "Varlık yönetimi")
    ]
)
class OpenAPIConfiguration : Application()

