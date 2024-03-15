package com.example.moneymarketshoprite.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime
import org.springframework.data.annotation.Id

@Entity(name = "transaction")
data class TransactionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var date: DateTime,
        var amount: Double,
        var description: String,
        var currency: String,
        var balance: Double)