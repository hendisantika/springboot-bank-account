package com.hendisantika.springbootbankaccount.controller;

import com.google.gson.Gson;
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
 * Time: 17:53
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DepositController.class)
public class DepositControllerTest extends BaseControllerTests {
    @Test
    public void testMaxDepositForTheDay() throws Exception {
        AccountTransaction transaction = new AccountTransaction(TransactionType.DEPOSIT.getId(), 100000, new Date());
        AccountTransaction transaction2 = new AccountTransaction(TransactionType.DEPOSIT.getId(), 40000, new Date());

        List<AccountTransaction> list = new ArrayList<>();
        list.add(transaction);
        list.add(transaction2);

        UserTransaction userTransaction = new UserTransaction(15000); // 3rd deposit $15K
        Gson gson = new Gson();
        String json = gson.toJson(userTransaction);

        given(this.transactionsService.findByDateBetweenAndType(AccountUtils.getStartOfDay(new Date()),
                AccountUtils.getEndOfDay(new Date()), TransactionType.DEPOSIT.getId())).willReturn(list);
        this.mvc.perform(post("/deposit/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(content().json("{\"success\":false,\"messages\":{\"message\":\"Deposit for the day should not be more than $150K\",\"title\":\"Error\"},\"errors\":{},\"data\":{},\"httpResponseCode\":406}"));
    }

}