package com.doye.space.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.util.function.Function

@Service
class JwtService(
    @Value("\${signing.key}")
    private val secretKey: String
) {
    private var expiration: Long = 864_000_000
    
    fun generateToken(userDetails : UserDetails) : String {
        val map: Map<String, Any> = mapOf()
        return generateToken(map, userDetails)
    }
    
    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails) : String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }
    
    fun isTokenValid(token : String, userDetails: UserDetails) : Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
    
    private fun isTokenExpired(token : String) : Boolean {
        return extractExpirationDate(token).before(Date())
    }
    
    fun extractExpirationDate(token : String) : Date {
        return extractClaims(token, Claims::getExpiration)
    }
    
    fun extractUsername(token : String) : String {
        return extractClaims(token, Claims::getSubject)
    }

    fun <T> extractClaims(token: String?, claimResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token!!)
        return claimResolver.apply(claims)
    }
    
    private fun extractAllClaims (token: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
    
    private fun getSigningKey() : Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}