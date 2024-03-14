package com.example.moneymarketshoprite

interface Account {
    fun handleDeposit(customerDepositCommand: DepositCommand)
    fun handleTransfer(customerTransferCommand: TransferCommand)
    fun handleGenerateReport(): List<TransactionReportResponse>
}