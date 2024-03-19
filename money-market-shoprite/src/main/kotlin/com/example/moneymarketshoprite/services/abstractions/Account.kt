package com.example.moneymarketshoprite.services.abstractions

import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand

interface Account {
    fun handleDeposit(customerDepositCommand: DepositCommand)
    fun handleTransfer(customerTransferCommand: TransferCommand)
    suspend fun handleGenerateTransactionReport(): List<TransactionReportResponse>
}