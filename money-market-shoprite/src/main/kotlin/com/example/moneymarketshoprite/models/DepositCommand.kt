package com.example.moneymarketshoprite.models

import java.math.BigDecimal

class DepositCommand(val depositAmount: BigDecimal, val currency: String)

//Currency to be Value Object