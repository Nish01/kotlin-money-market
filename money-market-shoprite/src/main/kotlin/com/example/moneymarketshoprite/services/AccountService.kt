package com.example.moneymarketshoprite.services

import com.example.moneymarketshoprite.enums.TransactionDescription
import com.example.moneymarketshoprite.models.AccountEntity
import com.example.moneymarketshoprite.services.abstractions.Account
import com.example.moneymarketshoprite.repositories.AccountRepository
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionEntity
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand
import com.example.moneymarketshoprite.repositories.TransactionRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AccountService(@Autowired private val accountRepository: AccountRepository, private val transactionRepository: TransactionRepository) : Account {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    @Transactional
    override fun handleDeposit(customerDepositCommand: DepositCommand, accountId: Long){
        var accountDetails = accountRepository.findById(accountId).orElse(null)
        val isAccountCurrencyValid = validateAccountCurrency(accountDetails, customerDepositCommand.currency)

        //Handle in transaction
        accountDetails.balance.plus(customerDepositCommand.depositAmount)
        logger.info("Account balance credited with deposit amount ${customerDepositCommand.depositAmount}")
        accountRepository.save(accountDetails)

        val transactionEntity = TransactionEntity(
                accountId = accountId,
                amount = customerDepositCommand.depositAmount,
                currencyCode = customerDepositCommand.currency,
                dateTime = LocalDateTime.now(),
                description = TransactionDescription.DEPOSIT.description
        )
        logger.info("Deposit transaction with deposit amount ${customerDepositCommand.depositAmount}")
        transactionRepository.save(transactionEntity)
    }

    @Transactional
    override fun handleTransfer(customerTransferCommand: TransferCommand, accountId: Long){
        var userAccountDetails = accountRepository.findById(accountId).orElse(null)
        var depositAccountDetails = accountRepository.findByAccountNumber(customerTransferCommand.destinationAccountNumber)

        //Validate transfer from account, check if account is in customerTransferCommand.currency otherwise need to convert amount
        //Likewise check if destination account is in customerTransferCommand.currency otherwise need to convert amount
        val isAccountCurrencyValid = validateAccountCurrency(userAccountDetails, customerTransferCommand.currency)
        val isDepositAccountCurrencyValid = validateAccountCurrency(depositAccountDetails, customerTransferCommand.currency)

        if(userAccountDetails.balance <  customerTransferCommand.transferAmount){
            //Throw error - insufficient balance to transfer - return
        }

        //Do transfer, add debit transaction record and add credit transaction record
        //Handle in transaction
        userAccountDetails.balance.minus(customerTransferCommand.transferAmount)
        logger.info("Account balance debited with transfer amount ${customerTransferCommand.transferAmount}")
        accountRepository.save(userAccountDetails)
        depositAccountDetails.balance.plus(customerTransferCommand.transferAmount)
        logger.info("Transfer account balance credited with transfer amount ${customerTransferCommand.transferAmount}")
        accountRepository.save(depositAccountDetails)


        val transferFromTransactionEntity = TransactionEntity(
                accountId = accountId,
                amount = -customerTransferCommand.transferAmount,
                currencyCode = customerTransferCommand.currency,
                dateTime = LocalDateTime.now(),
                description = TransactionDescription.TRANSFER.description
        )
        logger.info("Transfer transaction from account, with transfer amount ${customerTransferCommand.transferAmount}")
        transactionRepository.save(transferFromTransactionEntity)

        val transferToTransactionEntity = TransactionEntity(
                accountId = depositAccountDetails.id,
                amount = customerTransferCommand.transferAmount,
                currencyCode = customerTransferCommand.currency,
                dateTime = LocalDateTime.now(),
                description = TransactionDescription.TRANSFER.description
        )
        logger.info("Transfer transaction to account, with transfer amount ${customerTransferCommand.transferAmount}")
        transactionRepository.save(transferToTransactionEntity)
    }

    override suspend fun handleGenerateTransactionReport(accountId: Long) : List<TransactionReportResponse> {


    //Get list of transactions for user account. Default number of transactions, with Optional parameters: x number of transactions or min date for scalability


        return withContext(Dispatchers.IO) {
            // Perform long-running task to generate transaction report

            val accountId = 5L
            logger.info("Transaction report initiated for account: ${accountId}")

            var accountWithTransactions = accountRepository.findById(accountId).orElse(null) //need to add filter to limit records

            //Implement a Domain Method to map account Transactions to Transaction Report Response object to return to controller - return object hardcoded here
            logger.info("Transaction report generated for account: ${accountId}")
            listOf(
                    TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 1), 50.00, "ZAR", 150.00),
                    TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 20), -50.00, "ZAR", 100.00),
                    TransactionReportResponse(LocalDateTime.of(2024, 2, 1, 10, 38), 200.00, "ZAR", 200.00),
            )
        }
    }

    fun validateAccountCurrency(userAccountDetails: AccountEntity, currencyCodeToCheck: String) : Boolean{

        //Validate account, check account's currency
        //check if account is in customerDepositCommand.currency otherwise need to convert amount
        //Conversion implementation in a separate service, Conversion rates, this also implies multiple accounts - perhaps out of scope.
        //logger.info("Conversion from currency ZAR to USD required")
        return true
    }

}
