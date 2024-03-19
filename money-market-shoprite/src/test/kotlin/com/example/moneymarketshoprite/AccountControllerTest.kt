package com.example.moneymarketshoprite

import com.example.moneymarketshoprite.controllers.AccountController
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransactionResponse
import com.example.moneymarketshoprite.services.AccountService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime


class AccountControllerTest {

    @Mock
    lateinit var service: AccountService //Mocked service dependency

    @InjectMocks
    lateinit var controller: AccountController


    @Test
    fun `deposit method should return OK`() {
        //Mock request body
        val depositCommand = DepositCommand(
                depositAmount = BigDecimal(900),
                currency = "ZAR"
        )

        val accountId = 55L

        //Mock service behavior
        doNothing().`when`(service).handleDeposit(depositCommand, accountId)
        val controller = AccountController(service)
        //Call the deposit method and capture the response
        val response = controller.deposit(depositCommand)

        //Assert that the response status is OK and service method is called once
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(service, times(1)).handleDeposit(depositCommand, accountId);
    }

    @Test
    fun transfer() {
    }

    @Test
    suspend fun `generateTransactionReport should return OK when service returns TransactionReportResponse data`() {
        //Mock data
        val accountId = 123L
        val transactionReportResponse = getTransactionReportResponse()

        //Mock service behavior
        runBlocking {
            doReturn(getTransactionReportResponse()).`when`(service).handleGenerateTransactionReport(accountId)
        }

        //Call the function
        val response = controller.generateTransactionReport()

        // Verify response
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(transactionReportResponse, response.body)
        verify(service, times(1)).handleGenerateTransactionReport(accountId);
    }

    fun getTransactionReportResponse(): TransactionReportResponse {
       return TransactionReportResponse(
               transactionsResponse = listOf(
                       TransactionResponse(LocalDateTime.of(2024, 2, 1, 10, 1), BigDecimal(50.00), "ZAR"),
               ),
               openingBalance = BigDecimal(0),
               closingBalance = BigDecimal(20000)
       )
    }

}