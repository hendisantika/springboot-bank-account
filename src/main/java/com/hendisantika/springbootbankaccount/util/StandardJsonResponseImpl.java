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

    /**
     * @param success the success to set if success is false a default message and title is added
     */
    @Override
    public void setSuccess(boolean success) {
        this.success = success;
        if (!success) {
            messages.put(DEFAULT_MSG_NAME_FIELD, DEFAULT_MSG_NAME_VALUE);
            messages.put(DEFAULT_MSG_TITLE_FIELD, DEFAULT_MSG_TITLE_VALUE);
        }
    }

}
