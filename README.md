# springboot-bank-account

## Bank Account REST API
## What is this project for?

The goal of this mini project is to write a simple micro web service to mimic a “Bank Account”. Through this web service, one can query about the balance, deposit money, and withdraw money. Just like any Bank, there are restrictions on how many transactions/amounts it can handle. The details are described below.
- Write a simple “Bank Account” web service using REST API design principles.
- Program should have 3 REST API endpoints: Balance, Deposit, and Withdrawal
- No requirement for authentication assume the web service is for one account only and is open to the world
- No requirement for the backend store you can store it in a file or database (your decision)
- Balance endpoint this will return the outstanding balance
- Deposit endpoint credits the account with the specified amount
  - Max deposit for the day = $150K
  -  Max deposit per transaction = $40K
  -  Max deposit frequency = 4 transactions/day
- Withdrawal endpoint deducts the account with the specified amount
  - Max withdrawal for the day = $50K
  - Max withdrawal per transaction = $20K
  - Max withdrawal frequency = 3 transactions/day
  - Cannot withdraw when balance is less than withdrawal amount
- The service should handle all the error cases and return the appropriate error HTTP status code and error message (Eg. If an attempt is to withdraw greater than $20k in a single transaction, the error message should say “Exceeded Maximum Withdrawal Per Transaction”).

### How do I run the project? ###
* Execute ** start.sh **
* N.B.: Internet Connection needed to donwload jars from Maven Repo

### Rest Endpoints
#### http://localhost:8080/balance/ 			[GET]
#### http://localhost:8080/deposit/				[POST]
#### http://localhost:8080/withdrawal/			[POST]

### Executing API calls using Curl

* curl http://localhost:8585/balance/ 
* curl -H "Content-Type: application/json" -X POST -d '{"amount":25000}' http://localhost:8080/deposit/
* curl -H "Content-Type: application/json" -X POST -d '{"amount":15000}' http://localhost:8080/withdrawal/

> N.B.: Added backslash to escape on Windows OS

* curl -H "Content-Type: application/json" -X POST http://localhost:8080/deposit/ -d "{\"amount\":400}"