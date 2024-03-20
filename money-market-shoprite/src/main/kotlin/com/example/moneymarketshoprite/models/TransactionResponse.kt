package com.example.moneymarketshoprite.models

import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionResponse(var transactionDateTime: LocalDateTime, var amount: BigDecimal, var currency: String){
}

