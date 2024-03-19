package com.example.moneymarketshoprite.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import java.math.BigDecimal

@Entity(name = "account")
data class AccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        var id: Long?,
        @Column(name = "account_number", nullable = false)
        var accountNumber: Long,
        @Column(name = "currency_code", nullable = false)
        var currencyCode: String,
        @Column(name = "balance", nullable = false)
        var balance: BigDecimal,

        // @Column(name = "", nullable = false)
        // var token: String,

//        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "transaction")
//        var transactions: List<TransactionEntity>? = null
)