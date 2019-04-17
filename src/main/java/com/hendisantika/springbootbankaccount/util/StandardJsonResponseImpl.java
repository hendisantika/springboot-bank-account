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

    /**
     * @return the success
     */
    @Override
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return the messages
     */
    @Override
    public HashMap<String, String> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    @Override
    public void setMessages(HashMap<String, String> messages) {
        this.messages = messages;
    }

    /**
     * @return the errors
     */
    @Override
    public HashMap<String, String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    @Override
    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public HashMap<String, Object> getData() {
        return data;
    }

    @Override
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    /**
     * @param success
     * @param title   - message title
     * @param message -message body
     */
    @Override
    public void setSuccess(boolean success, String title, String message) {
        this.success = success;
        messages.put(DEFAULT_MSG_NAME_FIELD, (message == null || message.isEmpty()) ? "" : message);
        messages.put(DEFAULT_MSG_TITLE_FIELD, (title == null || title.isEmpty()) ? "" : title);
    }

    @Override
    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    @Override
    public void setHttpResponseCode(int code) {
        httpResponseCode = code;
    }

}
