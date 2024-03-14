package com.example.moneymarketshoprite

class TransferCommand(val userGuid: String, val accountNumber: Int, val depositAmount: Double,
                      val currency: String, destinationAccountNumber: Int)

//Currency to be Value Object
