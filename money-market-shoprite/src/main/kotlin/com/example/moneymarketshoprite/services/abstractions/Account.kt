package com.example.moneymarketshoprite.services.abstractions

import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand

interface Account {
    fun handleDeposit(customerDepositCommand: DepositCommand, accountId: Long)
    fun handleTransfer(customerTransferCommand: TransferCommand, accountId: Long)
    suspend fun handleGenerateTransactionReport(accountId: Long): List<TransactionReportResponse>
}