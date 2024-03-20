# kotlin-money-market
Kotlin sprint boot money market API

This application consists of an entry point via the Account Controller, with 3 endpoints:

1. deposit (PostMapping)
2. transfer (PostMapping)
3. generateTransactionReport (GetMapping)

The Account Service handles all the implementation for each endpoint.
Basic logging has been added and is configurable depending where the output is required. 
