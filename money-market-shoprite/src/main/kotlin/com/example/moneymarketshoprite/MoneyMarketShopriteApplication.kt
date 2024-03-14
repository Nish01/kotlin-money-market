package com.example.moneymarketshoprite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoneyMarketShopriteApplication

fun main(args: Array<String>) {
	runApplication<MoneyMarketShopriteApplication>(*args)
}


//Some Asumptions
//The authorized user is:
//The user who owns the account that is being deposited into (in the case of a deposit)
//The user who owns the account that money is being transferred out of (in the case of a transfer)
//This also implies the user only has 1 account then