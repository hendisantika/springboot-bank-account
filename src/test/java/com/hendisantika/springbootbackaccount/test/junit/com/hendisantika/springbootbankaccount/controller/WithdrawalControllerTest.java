package com.hendisantika.springbootbankaccount.controller;

import com.google.gson.Gson;
import com.hendisantika.springbootbankaccount.domain.Account;
import com.hendisantika.springbootbankaccount.domain.UserTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

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


}