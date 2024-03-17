package com.example.moneymarketshoprite.models

import java.time.LocalDateTime

class TransactionReportResponse(var transactionDateTime: LocalDateTime,var amount: Double, var currency: String, var balance: Double)