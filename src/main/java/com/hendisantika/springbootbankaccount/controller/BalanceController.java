package com.hendisantika.springbootbankaccount.controller;

import com.hendisantika.springbootbankaccount.domain.Account;
import com.hendisantika.springbootbankaccount.util.StandardJsonResponse;
import com.hendisantika.springbootbankaccount.util.StandardJsonResponseImpl;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
@RequestMapping(value = "/balance")
public class BalanceController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    StandardJsonResponse getBalance() {

        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        HashMap<String, Object> responseData = new HashMap<>();

        try {
            Account account = accountService.findOne(ACCOUNT_ID);

            if (account != null) {
                responseData.put("balance", "$" + account.getAmount());

                jsonResponse.setSuccess(true);
                jsonResponse.setData(responseData);
                jsonResponse.setHttpResponseCode(HttpStatus.SC_OK);
            } else {
                jsonResponse.setSuccess(false, "Resource not found", StandardJsonResponse.RESOURCE_NOT_FOUND_MSG);
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NO_CONTENT);
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
