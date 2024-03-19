package com.example.moneymarketshoprite.enums

enum class TransactionLogMessages(val message: String) {
    DEPOSIT("Deposit transaction with deposit amount"),
    TRANSFERFROM("Transfer transaction from account, with transfer amount"),
    TRANSFERTO("Transfer transaction to account, with transfer amount"),
    CREDIT("Account balance credited with deposit amount"),
    DEBIT("Account balance debited with transfer amount")
}