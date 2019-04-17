package com.hendisantika.springbootbankaccount.controller;

import com.google.gson.Gson;
import com.hendisantika.springbootbankaccount.domain.Account;
import com.hendisantika.springbootbankaccount.domain.AccountTransaction;
import com.hendisantika.springbootbankaccount.domain.TransactionType;
import com.hendisantika.springbootbankaccount.domain.UserTransaction;
import com.hendisantika.springbootbankaccount.util.AccountUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 17:54
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WithdrawalController.class)
public class WithdrawalControllerTest extends BaseControllerTests {
    @Test
    public void testWithdrawalExceedsCurrentBalance() throws Exception {

        UserTransaction userTransaction = new UserTransaction(50000);
        Gson gson = new Gson();
        String json = gson.toJson(userTransaction);

        given(this.accountService.findById(1L)).willReturn(Optional.of(new Account(40000)));

        this.mvc.perform(post("/withdrawal/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(content().json("{\"success\":false,\"messages\":{\"message\":\"You have insufficient funds\",\"title\":\"Error\"},\"errors\":{},\"data\":{},\"httpResponseCode\":406}"));

    }

    @Test
    public void testMaxWithdrawalForTheDay() throws Exception {

        AccountTransaction transaction = new AccountTransaction(TransactionType.WITHDRAWAL.getId(), 40000, new Date());
        AccountTransaction transaction2 = new AccountTransaction(TransactionType.WITHDRAWAL.getId(), 5000, new Date());

        List<AccountTransaction> list = new ArrayList<>();
        list.add(transaction);
        list.add(transaction2);

        UserTransaction userTransaction = new UserTransaction(8000);
        Gson gson = new Gson();
        String json = gson.toJson(userTransaction);

        given(this.accountService.findById(1L)).willReturn(Optional.of(new Account(400000)));

        given(this.transactionsService.findByDateBetweenAndType(AccountUtils.getStartOfDay(new Date()),
                AccountUtils.getEndOfDay(new Date()), TransactionType.WITHDRAWAL.getId())).willReturn(list);

        this.mvc.perform(post("/withdrawal/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(content().json("{\"success\":false,\"messages\":{\"message\":\"Withdrawal per day should not be more than $50K\",\"title\":\"Error\"},\"errors\":{},\"data\":{},\"httpResponseCode\":406}"));

    }



}