package com.hendisantika.springbootbankaccount.service;

import com.hendisantika.springbootbankaccount.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 07:05
 */
@Repository
public interface AccountService extends CrudRepository<Account, Long> {
}