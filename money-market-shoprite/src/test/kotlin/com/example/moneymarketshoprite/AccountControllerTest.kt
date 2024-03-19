package com.example.moneymarketshoprite

import com.example.moneymarketshoprite.controllers.AccountController
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.services.AccountService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    suspend fun `generate transaction report returns list of transactions`() {
        val accountId = 55L
        //Mock service behavior
        doReturn(getTransactionList()).`when`(service).handleGenerateTransactionReport(accountId)
        val controller = AccountController(service)

        // Call the deposit method and capture the response
        val response = controller.generateTransactionReport()

        //Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.statusCode)
        //Assert that the response body is a non-empty list service method is called once
        assertThat(response.body).isNotEmpty()
        assertEquals(getTransactionList().size, response.body?.size)
        verify(service, times(1)).handleGenerateTransactionReport(accountId);
    }

    fun getTransactionList(): List<TransactionReportResponse> {
       return listOf(
                TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 1), 50.00, "ZAR", 150.00),
                TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 20), -50.00, "ZAR", 100.00),
                TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 38), 200.00, "ZAR", 200.00),
        )
    }

}