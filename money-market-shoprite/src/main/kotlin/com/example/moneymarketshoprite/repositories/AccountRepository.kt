package com.example.moneymarketshoprite.repositories

import com.example.moneymarketshoprite.models.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long>{
    fun findByAccountNumber(accountNumber: Long): AccountEntity
}