package com.example.moneymarketshoprite.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime
import org.springframework.data.annotation.Id
import java.math.BigDecimal

@Entity(name = "transaction")
data class TransactionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        var id: Long?,
        @Column(name = "account_id", nullable = false)
        var AccountId: Long?,
        @Column(name = "date_time", nullable = false)
        var dateTime: DateTime,
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