package com.example.moneymarketshoprite

import com.example.moneymarketshoprite.enums.TransactionDescription
import com.example.moneymarketshoprite.models.AccountEntity
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.repositories.AccountRepository
import com.example.moneymarketshoprite.repositories.TransactionRepository
import com.example.moneymarketshoprite.services.AccountService
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.time.LocalDateTime

class AccountServiceTest {

    @Mock
    lateinit var fakeAccountRepository: AccountRepository

    @Mock
    lateinit var fakeTransactionRepository: TransactionRepository


    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Test
    fun `test handleDeposit updates balance and adds transaction`() {
        //Insert test data
        val originalBalance = BigDecimal("100.00")
        val accountDetails = AccountEntity(
                accountNumber = 12345L,
                currencyCode = "ZAR",
                balance = originalBalance)

        accountRepository.save(accountDetails)
        val accountId = accountDetails.id

        val customerDepositCommand = DepositCommand(
                depositAmount = BigDecimal("500.00"),
                currency = "ZAR",
        )

        val service = AccountService(accountRepository, transactionRepository)
        service.handleDeposit(customerDepositCommand, accountId!!)

        //Retrieve the updated account details from the database
        val updatedAccountDetails = accountRepository.findById(accountId).get()

        //Assert that the account balance was updated correctly
        val expectedUpdatedBalance = originalBalance + customerDepositCommand.depositAmount
        assertEquals(expectedUpdatedBalance, updatedAccountDetails.balance)

        //Assert that a transaction was created
        val transactions = transactionRepository.findAllByAccountId(accountId)
        assertEquals(1, transactions.size)
        val transaction = transactions[0]
        assertEquals(accountId, transaction.accountId)
        assertEquals(customerDepositCommand.depositAmount, transaction.amount)
        assertEquals(customerDepositCommand.currency, transaction.currencyCode)
        assertEquals(TransactionDescription.DEPOSIT.description, transaction.description)
        assertTrue(transaction.dateTime.isAfter(LocalDateTime.now().minusMinutes(1))) // Assert transaction datetime is recent

        verify(accountRepository, times(1)).save(updatedAccountDetails);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    fun handleTransfer() {
    }

    @Test
    fun handleGenerateTransactionReport() {
    }
}