package com.example.moneymarketshoprite

class DepositCommand(val userGuid: String, val accountNumber: Int, val depositAmount: Double,
                                    val currency: String)

//Currency to be Value Object