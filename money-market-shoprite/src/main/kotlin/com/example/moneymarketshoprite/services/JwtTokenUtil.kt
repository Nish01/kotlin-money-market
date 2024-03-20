package com.example.moneymarketshoprite.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*

object JwtTokenUtil {
    private const val SECRET = ""
    fun generateToken(userId: Long, roles: List<String>): String {
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray())
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + 86400000)) //Token valid for 1 day
                .signWith(key)
                .compact()
    }
}