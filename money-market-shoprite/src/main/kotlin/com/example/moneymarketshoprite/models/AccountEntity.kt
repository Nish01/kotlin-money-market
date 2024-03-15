package com.example.moneymarketshoprite.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity(name = "account")
data class AccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var accountNumber: Long,
        var name: String,
        var token: String,
        var currency: String)