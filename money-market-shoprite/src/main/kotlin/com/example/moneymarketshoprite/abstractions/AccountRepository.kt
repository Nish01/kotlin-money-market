package com.example.moneymarketshoprite.abstractions

import com.example.moneymarketshoprite.models.AccountEntity
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<AccountEntity, Long>