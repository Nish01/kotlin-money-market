package com.example.moneymarketshoprite.models

import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionReportResponse(var transactionsResponse: List<TransactionResponse>,
                                var openingBalance: BigDecimal, var closingBalance: BigDecimal){
}

