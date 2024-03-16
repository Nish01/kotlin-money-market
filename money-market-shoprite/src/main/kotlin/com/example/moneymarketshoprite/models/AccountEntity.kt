package com.example.moneymarketshoprite.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.OneToMany
import org.springframework.data.annotation.Id

@Entity(name = "account")
data class AccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var accountNumber: Long,
        var name: String,
        var token: String,
        var currency: String,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "transaction")
        var transactions: List<TransactionEntity>? = null
)