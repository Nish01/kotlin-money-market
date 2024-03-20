package com.example.moneymarketshoprite.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction", schema = "kmm", catalog = "moneymarket")
data class TransactionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        var id: Long? = null,
        @Column(name = "account_id", nullable = false)
        var accountId: Long?,
        @Column(name = "date_time", nullable = false)
        var dateTime: LocalDateTime,
        @Column(name = "amount", nullable = false)
        var amount: BigDecimal,
        @Column(name = "description", nullable = true)
        var description: String,
        @Column(name = "currency_code", nullable = false)
        var currencyCode: String,

//        @ManyToOne
//        @JoinColumn(name = "transaction_id")
//        var account: AccountEntity? = null
)