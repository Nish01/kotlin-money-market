package com.example.moneymarketshoprite.repositories

import com.example.moneymarketshoprite.models.AccountEntity
import com.example.moneymarketshoprite.models.TransactionEntity
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<TransactionEntity, Long>{
    fun findAllByAccountId(accountId: Long): List<TransactionEntity>
}