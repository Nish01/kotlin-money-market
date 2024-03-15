package com.example.moneymarketshoprite

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity(name = "transaction")
data class TransactionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var amount: Double,
        var description: String,
        var currency: String)