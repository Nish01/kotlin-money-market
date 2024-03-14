package com.example.moneymarketshoprite

import org.springframework.stereotype.Service

@Service
class AccountService : Account {

    override fun handleDeposit(customerDepositCommand: DepositCommand){

        //Get user JWT token that will have already been authorised and exchanged, hard coded here
        val userToken = "xxxx"

        //get user account details using userToken

        //Validate account, check account's currency
        //check if account is in customerDepositCommand.currency otherwise convert amount
        //Implementation in a separate service, this also implies multiple accounts - perhaps out of scope.
        //Logging if conversion needed


        //Handle in transaction
        //Db call to insert deposit transaction
        //Logging of deposit amount - account number partially stored in logs

    }

    override fun handleTransfer(customerTransferCommand: TransferCommand){
        //Get user JWT token that will have already been authorised and exchanged, hard coded here
        val userToken = "xxxx"

        //Get user account details using userToken
        //Validate transfer from account, check if account is in customerTransferCommand.currency otherwise convert amount
        //Implementation in a separate service, this also implies multiple accounts - perhaps out of scope.
        //Logging if converted
        //Likewise check if destination account is in customerTransferCommand.currency otherwise convert amount

        //Check if first account balance has sufficient funds before being allowed to transfer

        //Do transfer Service to handle it, add debit transaction record and add credit transaction record - db call, handle it all as a transaction




    }

    override fun handleGenerateReport() : List<TransactionReportResponse>{
        //Get list of transactions. Default number of transactions, with Optional parameters: x number of transactions or min date for scalability
        //Logging - Initiating get report data

       return listOf(
                TransactionReportResponse(50.00, "ZAR", 150.00),
                TransactionReportResponse(-50.00, "ZAR", 100.00),
                TransactionReportResponse(200.00, "ZAR", 200.00),
        )
    }

}
