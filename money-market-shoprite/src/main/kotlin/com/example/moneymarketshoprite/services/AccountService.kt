package com.example.moneymarketshoprite.services

import com.example.moneymarketshoprite.enums.TransactionDescription
import com.example.moneymarketshoprite.enums.TransactionLogMessages
import com.example.moneymarketshoprite.models.AccountEntity
import com.example.moneymarketshoprite.services.abstractions.Account
import com.example.moneymarketshoprite.repositories.AccountRepository
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionEntity
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransactionResponse
import com.example.moneymarketshoprite.models.TransferCommand
import com.example.moneymarketshoprite.repositories.TransactionRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AccountService(@Autowired private val accountRepository: AccountRepository,
                     @Autowired private val transactionRepository: TransactionRepository) : Account {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    @Transactional
    override fun handleDeposit(customerDepositCommand: DepositCommand, accountId: Long){
        var accountDetails = accountRepository.findById(accountId).orElse(null)
        val isAccountCurrencyValid = validateAccountCurrency(accountDetails, customerDepositCommand.currency)

        //Handle in transaction
        creditAccountBalance(accountDetails, customerDepositCommand.depositAmount)

        createTransaction(accountId, customerDepositCommand.depositAmount, customerDepositCommand.currency,
                TransactionDescription.DEPOSIT.description,
                "${TransactionLogMessages.DEPOSIT.message} ${customerDepositCommand.currency} ${customerDepositCommand.depositAmount}")
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
            //Return error - insufficient balance to transfer
        }

        //Do transfer, update balances, add debit transaction record and add credit transaction record
        debitAccountBalance(userAccountDetails, customerTransferCommand.transferAmount, false)
        creditAccountBalance(depositAccountDetails, customerTransferCommand.transferAmount, false)

        //Handle in transaction
        accountRepository.saveAll(listOf(userAccountDetails, depositAccountDetails))

        val transferFromTransactionEntity = createTransaction(accountId, -customerTransferCommand.transferAmount,
                customerTransferCommand.currency, TransactionDescription.TRANSFER.description,
                "${TransactionLogMessages.TRANSFERFROM.message} ${customerTransferCommand.currency} ${customerTransferCommand.transferAmount}", false)

        val transferToTransactionEntity = createTransaction(depositAccountDetails.id!!, customerTransferCommand.transferAmount,
                customerTransferCommand.currency, TransactionDescription.TRANSFER.description,
                "${TransactionLogMessages.TRANSFERTO.message} ${customerTransferCommand.currency} ${customerTransferCommand.transferAmount}", false)

        transactionRepository.saveAll(listOf(transferFromTransactionEntity, transferToTransactionEntity))
    }

    override suspend fun handleGenerateTransactionReport(accountId: Long) : TransactionReportResponse {
        //Default number of transactions, with Optional parameters: x number of transactions or min date for optimisation and extensibility

        return withContext(Dispatchers.IO) {
            //Perform long-running task to generate transaction report

            logger.info("Transaction report initiated for account: ${accountId}")
            var userAccountDetails = accountRepository.findById(accountId).orElse(null)
            var transactions = transactionRepository.findAllByAccountId(accountId) //Add filter to limit records

            val transactionsResponse = ArrayList<TransactionResponse>()
            for (transaction in transactions) {
                var transactionResponse = mapToTransactionReportResponse(transaction)
                transactionsResponse.add(transactionResponse)
            }

            logger.info("Transaction report generated for account: ${accountId}")
            TransactionReportResponse(
                    transactionsResponse = transactionsResponse,
                    openingBalance = BigDecimal(0),
                    closingBalance = userAccountDetails.balance
            )
        }
    }

    private fun validateAccountCurrency(userAccountDetails: AccountEntity, currencyCodeToCheck: String) : Boolean{
        //Validate account, check account's currency
        //check if account is in customerDepositCommand.currency otherwise need to convert amount
        //Conversion implementation in a separate service, Conversion rates, this also implies multiple accounts - perhaps out of scope.
        //logger.info("Conversion from currency ZAR to USD required")
        return true
    }

    private fun creditAccountBalance(userAccountDetails: AccountEntity, amount: BigDecimal, callSave: Boolean = true){
        userAccountDetails.balance.plus(amount)
        logger.info("${TransactionLogMessages.CREDIT.message} ${userAccountDetails.currencyCode} ${amount}")

        if(callSave) {
            accountRepository.save(userAccountDetails)
        }
    }

    private fun debitAccountBalance(userAccountDetails: AccountEntity, amount: BigDecimal, callSave: Boolean = true){
        userAccountDetails.balance.minus(amount)
        logger.info("${TransactionLogMessages.DEBIT.message} ${userAccountDetails.currencyCode} ${amount}")

        if(callSave) {
            accountRepository.save(userAccountDetails)
        }
    }

    private fun createTransaction(accountId: Long, amount: BigDecimal, currencyCode: String,
                                  transactionDescription: String, logMessage: String, callSave: Boolean = true): TransactionEntity {

        val transactionEntity = TransactionEntity(
                accountId = accountId,
                amount = amount,
                currencyCode = currencyCode,
                dateTime = LocalDateTime.now(),
                description = transactionDescription
        )
        logger.info(logMessage)

        if(callSave) {
            transactionRepository.save(transactionEntity)
        }

        return transactionEntity
    }

   private fun mapToTransactionReportResponse(transactionEntity: TransactionEntity): TransactionResponse {
        return TransactionResponse(
                transactionDateTime= transactionEntity.dateTime,
                amount = transactionEntity.amount,
                currency = transactionEntity.currencyCode,
        )
    }
}
