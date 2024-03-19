package com.example.moneymarketshoprite.repositories

import com.example.moneymarketshoprite.models.AccountEntity
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<AccountEntity, Long>{
    fun findByAccountNumber(accountNumber: Long): AccountEntity
}