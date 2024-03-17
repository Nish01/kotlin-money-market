package com.example.moneymarketshoprite

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

        service.handleDeposit(customerDepositCommand)
        return status(HttpStatus.OK).build()
    }

    @PutMapping()
    fun transfer(@RequestBody customerTransferCommand: TransferCommand): ResponseEntity<Unit> {
        //Check user validation - can transfer

        service.handleTransfer(customerTransferCommand)
        return status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/transactions")
    suspend fun generateTransactionReport(): ResponseEntity<List<TransactionReportResponse>> {
        var transactionList = service.handleGenerateTransactionReport()

        return if (transactionList != null) ResponseEntity(transactionList, HttpStatus.OK)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
