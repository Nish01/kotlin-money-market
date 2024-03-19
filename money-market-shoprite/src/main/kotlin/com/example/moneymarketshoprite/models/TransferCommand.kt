package com.example.moneymarketshoprite.models

import java.math.BigDecimal

class TransferCommand(val depositAmount: BigDecimal, val currency: String,
                      val destinationAccountNumber: Long)

//Currency to be Value Object
