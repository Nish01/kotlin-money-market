package com.example.moneymarketshoprite.controllers

import com.example.moneymarketshoprite.services.AccountService
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccountController(@Autowired val service: AccountService) {

    @PostMapping("/deposit")
    fun deposit(@RequestBody customerDepositCommand: DepositCommand): ResponseEntity<Unit> {
        //Check user validation - can deposit
        //Get user token that will have already been authorised and exchanged, hard coded here
        //Need to think a bit deeper on using the token in Db to connect to the user
        val userToken = "xxxx"

        //Get user account details using userToken
        val accountId = 555L

        //Switch to Mediator pattern
        service.handleDeposit(customerDepositCommand, accountId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/transfer")
    fun transfer(@RequestBody customerTransferCommand: TransferCommand): ResponseEntity<Unit> {
        //Check user validation - can transfer

        //Get user token that will have already been authorised and exchanged, hard coded here
        //Need to think a bit deeper on using the token in Db to connect to the user
        val userToken = "xxxx"
        //get user account details using userToken
        val accountId = 555L

        service.handleTransfer(customerTransferCommand, accountId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/transactions")
    suspend fun generateTransactionReport(): ResponseEntity<List<TransactionReportResponse>> {
        //Check user validation - can generate transaction report

        //Get user token that will have already been authorised and exchanged, hard coded here
        //Need to think a bit deeper on using the token in Db to connect to the user
        val userToken = "xxxx"
        val accountId = 555L

        var transactionList = service.handleGenerateTransactionReport(accountId)

        return if (transactionList != null) ResponseEntity.ok(transactionList)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
