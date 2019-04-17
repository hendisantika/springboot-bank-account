package com.hendisantika.springbootbankaccount.domain;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:04
 */
public enum TransactionType {

    DEPOSIT(1), WITHDRAWAL(2);
    int id;

    TransactionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}