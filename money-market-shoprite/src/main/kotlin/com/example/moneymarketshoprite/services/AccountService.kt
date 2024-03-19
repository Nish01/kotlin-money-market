package com.example.moneymarketshoprite.services

import com.example.moneymarketshoprite.abstractions.Account
import com.example.moneymarketshoprite.abstractions.AccountRepository
import com.example.moneymarketshoprite.controllers.AccountController
import com.example.moneymarketshoprite.models.DepositCommand
import com.example.moneymarketshoprite.models.TransactionReportResponse
import com.example.moneymarketshoprite.models.TransferCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AccountService(@Autowired private val accountRepository: AccountRepository) : Account {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    override fun handleDeposit(customerDepositCommand: DepositCommand){
        //Get user token that will have already been authorised and exchanged, hard coded here
        //Need to think a bit deeper on using the token in Db to connect to the user
        val userToken = "xxxx"

        //get user account details using userToken
        val accountId = 5L
        var accountDetails = accountRepository.findById(accountId).orElse(null) //filter to limit transaction records

        //Validate account, check account's currency
        //check if account is in customerDepositCommand.currency otherwise convert amount
        //Implementation in a separate service, this also implies multiple accounts - perhaps out of scope.
        logger.info("Conversion from currency  to  required")

        //Handle in transaction
        //Db call to insert deposit transaction

        //Get last record to update balance
        //Logging of deposit amount - account number partially stored in logs
    }

    override fun handleTransfer(customerTransferCommand: TransferCommand){
        //Get user token that will have already been authorised and exchanged, hard coded here
        //Need to think a bit deeper on using the token in Db to connect to the user
        val userToken = "xxxx"

        //Get user account details using userToken
        val accountId = 5L
        var userAccountDetails = accountRepository.findById(accountId).orElse(null) //filter to limit transaction records

        var depositAccountDetails = accountRepository.findByAccountNumber(customerTransferCommand.destinationAccountNumber) //filter to limit transaction records


        //Validate transfer from account, check if account is in customerTransferCommand.currency otherwise convert amount
        //Implementation in a separate service, this also implies multiple accounts - perhaps out of scope.
        logger.info("Conversion from currency  to  required")
        //Likewise check if destination account is in customerTransferCommand.currency otherwise convert amount

        //Check if first account balance has sufficient funds before being allowed to transfer

        //Do transfer, add debit transaction record and add credit transaction record - db call, handle it all as a transaction
        //Get last record to update balance for both transactions
    }

    override suspend fun handleGenerateTransactionReport() : List<TransactionReportResponse> {
    //Get user token that will have already been authorised and exchanged, hard coded here
    //Need to think a bit deeper on using the token in Db to connect to the user
    val userToken = "xxxx"

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

}
