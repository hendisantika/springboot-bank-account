package com.hendisantika.springbootbankaccount.controller;

import com.hendisantika.springbootbankaccount.domain.Account;
import com.hendisantika.springbootbankaccount.domain.AccountTransaction;
import com.hendisantika.springbootbankaccount.domain.TransactionType;
import com.hendisantika.springbootbankaccount.domain.UserTransaction;
import com.hendisantika.springbootbankaccount.util.AccountUtils;
import com.hendisantika.springbootbankaccount.util.StandardJsonResponse;
import com.hendisantika.springbootbankaccount.util.StandardJsonResponseImpl;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:11
 */
@RestController
@RequestMapping("/deposit")
public class DepositController extends BaseController {

    private static final double MAX_DEPOSIT_PER_TRANSACTION = 40000; // $40k
    private static final double MAX_DEPOSIT_PER_DAY = 150000; // $150k
    private static final int MAX_DEPOSIT_TRANSACTIONS_PER_DAY = 4;

    @PostMapping("/")
    public @ResponseBody
    StandardJsonResponse makeDeposit(@RequestBody UserTransaction userTransaction) {

        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();

        try {

            double total = 0;

            // check maximum limit deposit for the day has been reached
            List<AccountTransaction> deposits = transactionsService.findByDateBetweenAndType(AccountUtils.getStartOfDay(new Date()),
                    AccountUtils.getEndOfDay(new Date()), TransactionType.DEPOSIT.getId());

            if (deposits.size() > 0) {
                for (AccountTransaction accountTransaction : deposits) {
                    total += accountTransaction.getAmount();
                }
                if (total + userTransaction.getAmount() > MAX_DEPOSIT_PER_DAY) {
                    jsonResponse.setSuccess(false, "Error", "Deposit for the day should not be more than $150K");
                    jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                    return jsonResponse;
                }
            }

            // Check whether the amount being deposited exceeds the MAX_DEPOSIT_PER_TRANSACTION
            if (userTransaction.getAmount() > MAX_DEPOSIT_PER_TRANSACTION) {
                jsonResponse.setSuccess(false, "Error", "Deposit per transaction should not be more than $40K");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                return jsonResponse;
            }

            // check whether transactions exceeds the max allowed per day
            if (deposits.size() < MAX_DEPOSIT_TRANSACTIONS_PER_DAY) {
                AccountTransaction accountTransaction = new AccountTransaction(TransactionType.DEPOSIT.getId(), userTransaction.getAmount(), new Date());
                double amount = transactionsService.save(accountTransaction).getAmount();

                Optional<Account> account = accountService.findById(ACCOUNT_ID);
                double newBalance = account.get().getAmount() + amount;
                account.get().setAmount(newBalance);

                Account account1 = account.get();
                accountService.save(account1);

                jsonResponse.setSuccess(true, "", "Deposit sucessfully Transacted");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_OK);

            } else {
                jsonResponse.setSuccess(false, "Error", "maximum transactions for the day Exceeded");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
            }

        } catch (Exception e) {
            logger.error("exception", e);
            jsonResponse.setSuccess(false, StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
            jsonResponse.setHttpResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return jsonResponse;
        }

        return jsonResponse;
    }

}
