package com.hendisantika.springbootbankaccount.util;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:07
 */
public class StandardJsonResponseImpl implements StandardJsonResponse {
    private boolean success = false;

    private HashMap<String, String> messages;

    private HashMap<String, String> errors;

    private HashMap<String, Object> data;

    private int httpResponseCode;

    public StandardJsonResponseImpl() {

        messages = new HashMap<String, String>();
        errors = new HashMap<String, String>();
        data = new HashMap<String, Object>();
    }

}
