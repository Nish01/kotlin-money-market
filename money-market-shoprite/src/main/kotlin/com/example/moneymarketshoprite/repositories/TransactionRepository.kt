package com.example.moneymarketshoprite.repositories

import com.example.moneymarketshoprite.models.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<TransactionEntity, Long>{
    fun findAllByAccountId(accountId: Long): List<TransactionEntity>
}