package com.pawcial.util

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import java.time.Instant

/**
 * JWT Token yardımcı sınıfı
 *
 * JWT token'larından bilgi çıkarmak ve validate etmek için utility metodlar sağlar
 */
@ApplicationScoped
class JwtUtils {

    private val logger = Logger.getLogger(JwtUtils::class.java)

    /**
     * SecurityIdentity'den JWT token'ı extract eder
     */
    fun extractToken(securityIdentity: SecurityIdentity): JsonWebToken? {
        return try {
            securityIdentity.principal as? JsonWebToken
        } catch (e: Exception) {
            logger.error("Error extracting JWT token: ${e.message}")
            null
        }
    }

    /**
     * Token'dan kullanıcı ID'sini (subject) alır
     */
    fun getUserId(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.principal?.name
    }

    /**
     * Token'dan email adresini alır
     */
    fun getEmail(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.attributes["email"]?.toString()
    }

    /**
     * Token'dan kullanıcı adını alır
     */
    fun getUsername(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.attributes["preferred_username"]?.toString()
            ?: securityIdentity.attributes["username"]?.toString()
    }

    /**
     * Token'dan tam adı alır
     */
    fun getFullName(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.attributes["name"]?.toString()
    }

    /**
     * Token'dan given name (ad) alır
     */
    fun getGivenName(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.attributes["given_name"]?.toString()
    }

    /**
     * Token'dan family name (soyad) alır
     */
    fun getFamilyName(securityIdentity: SecurityIdentity): String? {
        return securityIdentity.attributes["family_name"]?.toString()
    }

    /**
     * Kullanıcının belirli bir role sahip olup olmadığını kontrol eder
     */
    fun hasRole(securityIdentity: SecurityIdentity, role: String): Boolean {
        return securityIdentity.hasRole(role)
    }

    /**
     * Kullanıcının belirtilen rollerden herhangi birine sahip olup olmadığını kontrol eder
     */
    fun hasAnyRole(securityIdentity: SecurityIdentity, vararg roles: String): Boolean {
        return roles.any { securityIdentity.hasRole(it) }
    }

    /**
     * Kullanıcının tüm belirtilen rollere sahip olup olmadığını kontrol eder
     */
    fun hasAllRoles(securityIdentity: SecurityIdentity, vararg roles: String): Boolean {
        return roles.all { securityIdentity.hasRole(it) }
    }

    /**
     * Token'ın tüm claim'lerini Map olarak döner
     */
    fun getAllClaims(securityIdentity: SecurityIdentity): Map<String, Any?> {
        return securityIdentity.attributes.mapValues { it.value }
    }

    /**
     * Token'dan belirli bir claim değerini alır
     */
    fun getClaim(securityIdentity: SecurityIdentity, claimName: String): Any? {
        return securityIdentity.attributes[claimName]
    }

    /**
     * Token'ın expire olup olmadığını kontrol eder
     */
    fun isTokenExpired(jwt: JsonWebToken): Boolean {
        val exp = jwt.expirationTime
        return exp < Instant.now().epochSecond
    }

    /**
     * Token'ın kalan süresini saniye olarak döner
     */
    fun getTokenRemainingTime(jwt: JsonWebToken): Long {
        val exp = jwt.expirationTime
        val now = Instant.now().epochSecond
        return maxOf(0, exp - now)
    }

    /**
     * Kullanıcının email adresinin doğrulanmış olup olmadığını kontrol eder
     */
    fun isEmailVerified(securityIdentity: SecurityIdentity): Boolean {
        return securityIdentity.attributes["email_verified"]?.toString()?.toBoolean() ?: false
    }

    /**
     * Token'dan issuer (yayıncı) bilgisini alır
     */
    fun getIssuer(jwt: JsonWebToken): String {
        return jwt.issuer ?: "unknown"
    }

    /**
     * Token'dan audience (hedef kitle) bilgisini alır
     */
    fun getAudience(jwt: JsonWebToken): Set<String> {
        return jwt.audience ?: emptySet()
    }

    /**
     * Kullanıcının realm access rollerini alır
     */
    fun getRealmRoles(securityIdentity: SecurityIdentity): List<String> {
        val realmAccess = securityIdentity.attributes["realm_access"] as? Map<*, *>
        val roles = realmAccess?.get("roles") as? List<*> ?: emptyList<Any>()
        return roles.filterIsInstance<String>()
    }

    /**
     * Kullanıcının client (resource) rollerini alır
     */
    fun getClientRoles(securityIdentity: SecurityIdentity, clientId: String): List<String> {
        val resourceAccess = securityIdentity.attributes["resource_access"] as? Map<*, *>
        val clientAccess = resourceAccess?.get(clientId) as? Map<*, *>
        val roles = clientAccess?.get("roles") as? List<*> ?: emptyList<Any>()
        return roles.filterIsInstance<String>()
    }

    /**
     * Token bilgilerini loglama için formatlı string döner
     */
    fun formatTokenInfo(securityIdentity: SecurityIdentity): String {
        return buildString {
            appendLine("=== JWT Token Info ===")
            appendLine("User ID: ${getUserId(securityIdentity)}")
            appendLine("Username: ${getUsername(securityIdentity)}")
            appendLine("Email: ${getEmail(securityIdentity)}")
            appendLine("Full Name: ${getFullName(securityIdentity)}")
            appendLine("Email Verified: ${isEmailVerified(securityIdentity)}")
            appendLine("Roles: ${securityIdentity.roles.joinToString(", ")}")
            appendLine("=====================")
        }
    }
}

