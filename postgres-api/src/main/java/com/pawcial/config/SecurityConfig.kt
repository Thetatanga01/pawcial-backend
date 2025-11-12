package com.pawcial.config

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger

/**
 * Security Configuration for Keycloak OIDC Integration
 *
 * Bu sınıf Keycloak entegrasyonu için gerekli güvenlik yapılandırmasını sağlar.
 * Tüm konfigürasyon parametrik olarak environment variable'lardan alınır.
 */
@ApplicationScoped
class SecurityConfig {

    @Inject
    private lateinit var logger: Logger

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    private lateinit var authServerUrl: String

    @ConfigProperty(name = "quarkus.oidc.client-id")
    private lateinit var clientId: String

    @ConfigProperty(name = "quarkus.keycloak.policy-enforcer.enable", defaultValue = "true")
    var policyEnforcerEnabled: Boolean = false

    /**
     * Keycloak konfigürasyonunu loglar
     */
    fun logConfiguration() {
        logger.info("=== Keycloak Configuration ===")
        logger.info("Auth Server URL: $authServerUrl")
        logger.info("Client ID: $clientId")
        logger.info("Policy Enforcer Enabled: $policyEnforcerEnabled")
        logger.info("==============================")
    }

    /**
     * SecurityIdentity'den kullanıcı bilgilerini çıkarır
     */
    fun extractUserInfo(securityIdentity: SecurityIdentity): UserInfo {
        val attributes = securityIdentity.attributes

        return UserInfo(
            userId = securityIdentity.principal?.name ?: "anonymous",
            email = attributes["email"]?.toString(),
            name = attributes["name"]?.toString(),
            preferredUsername = attributes["preferred_username"]?.toString(),
            roles = securityIdentity.roles.toSet(),
            attributes = attributes.mapValues { it.value?.toString() }
        )
    }
}

/**
 * Kullanıcı bilgileri DTO
 */
data class UserInfo(
    val userId: String,
    val email: String?,
    val name: String?,
    val preferredUsername: String?,
    val roles: Set<String>,
    val attributes: Map<String, String?>
)

