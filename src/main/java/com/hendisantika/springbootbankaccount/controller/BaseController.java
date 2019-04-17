package com.hendisantika.springbootbankaccount.controller;

import com.hendisantika.springbootbankaccount.service.AccountService;
import com.hendisantika.springbootbankaccount.service.TransactionsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:10
 */
public class BaseController {
    protected static final long ACCOUNT_ID = 1L;
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    AccountService accountService;

    @Autowired
    TransactionsService transactionsService;
}
