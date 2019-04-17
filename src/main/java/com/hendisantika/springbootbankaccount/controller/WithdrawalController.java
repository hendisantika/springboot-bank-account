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

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:12
 */
@RestController
@RequestMapping(value = "/withdrawal")
public class WithdrawalController extends BaseController {

    private static final double MAX_WITHDRAWAL_PER_TRANSACTION = 20000; // $20k
    private static final double MAX_WITHDRAWAL_PER_DAY = 50000; // $50k
    private static final int MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY = 3;

    @PostMapping("/")
    public @ResponseBody
    StandardJsonResponse makeWithDrawal(@RequestBody UserTransaction userTransaction) {

        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();

        try {

            double total = 0;

            // check balance
            double balance = accountService.findOne(ACCOUNT_ID).getAmount();
            if (userTransaction.getAmount() > balance) {
                jsonResponse.setSuccess(false, "Error", "You have insufficient funds");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                return jsonResponse;
            }


            // check maximum limit withdrawal for the day has been reached
            List<AccountTransaction> withdrawals = transactionsService.findByDateBetweenAndType(AccountUtils.getStartOfDay(new Date()),
                    AccountUtils.getEndOfDay(new Date()), TransactionType.WITHDRAWAL.getId());

            if (withdrawals.size() > 0) {
                for (AccountTransaction accountTransaction : withdrawals) {
                    total += accountTransaction.getAmount();
                }
                if (total + userTransaction.getAmount() > MAX_WITHDRAWAL_PER_DAY) {
                    jsonResponse.setSuccess(false, "Error", "Withdrawal per day should not be more than $50K");
                    jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                    return jsonResponse;
                }
            }

            // Check whether the amount being withdrawn exceeds the MAX_WITHDRAWAL_PER_TRANSACTION
            if (userTransaction.getAmount() > MAX_WITHDRAWAL_PER_TRANSACTION) {
                jsonResponse.setSuccess(false, "Error", "Exceeded Maximum Withdrawal Per Transaction");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                return jsonResponse;
            }

            // check whether transactions exceeds the max allowed per day
            if (withdrawals.size() < MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY) {
                AccountTransaction accountTransaction = new AccountTransaction(TransactionType.WITHDRAWAL.getId(), userTransaction.getAmount(), new Date());
                double amount = transactionsService.save(accountTransaction).getAmount();

                Account account = accountService.findOne(ACCOUNT_ID);
                double newBalance = account.getAmount() - amount;
                account.setAmount(newBalance);
                accountService.save(account);

                jsonResponse.setSuccess(true, "", "Withdrawal sucessfully Transacted");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_OK);

            } else {
                jsonResponse.setSuccess(false, "Error", "Maximum Withdrawal transactions for the day Exceeded");
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
