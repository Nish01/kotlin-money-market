package com.example.moneymarketshoprite.models

class TransferCommand(val depositAmount: Double, val currency: String,
                      val destinationAccountNumber: Long)

//Currency to be Value Object
