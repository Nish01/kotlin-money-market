package com.example.moneymarketshoprite.controllers

import com.example.moneymarketshoprite.services.JwtTokenUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AuthController() {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: String): ResponseEntity<String> {
        //Authenticate user

        //Assume user is authenticated and get user details
        val userId = 555L
        val roles = listOf("ROLE_DEPOSIT")

        //Generate JWT token
        val token = JwtTokenUtil.generateToken(userId, roles)

        return ResponseEntity.ok(token)
    }

}
