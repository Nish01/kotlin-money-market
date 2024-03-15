package com.example.moneymarketshoprite

import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccountController(val service: AccountService) {

    @PostMapping()
    fun deposit(@RequestBody customerDepositCommand: DepositCommand): ResponseEntity<Unit> {
        //Check user validation - can deposit

        service.handleDeposit(customerDepositCommand)
        return status(HttpStatus.OK).build()
    }

    @PostMapping()
    fun transfer(@RequestBody customerTransferCommand: TransferCommand): ResponseEntity<Unit> {
        //Check user validation - can transfer

        service.handleTransfer(customerTransferCommand)
        return status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/")
    fun generateReport(): List<TransactionReportResponse> {
        var transactionList = service.handleGenerateReport()

        return transactionList
    }
}
