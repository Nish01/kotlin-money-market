package com.example.moneymarketshoprite.controllers

import com.example.moneymarketshoprite.services.AccountService
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccountController(@Autowired val service: AccountService) {

    @PutMapping()
    fun deposit(@RequestBody customerDepositCommand: DepositCommand): ResponseEntity<Unit> {
        //Check user validation - can deposit
        //Switch to Mediator pattern

        service.handleDeposit(customerDepositCommand)
        return ResponseEntity.ok().build()
    }

    @PutMapping()
    fun transfer(@RequestBody customerTransferCommand: TransferCommand): ResponseEntity<Unit> {
        //Check user validation - can transfer

        service.handleTransfer(customerTransferCommand)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/transactions")
    suspend fun generateTransactionReport(): ResponseEntity<List<TransactionReportResponse>> {
        //Check user validation - can generate transaction report

        var transactionList = service.handleGenerateTransactionReport()

        return if (transactionList != null) ResponseEntity.ok(transactionList)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
