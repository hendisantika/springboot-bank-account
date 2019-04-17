package com.hendisantika.springbootbankaccount.service;

import com.hendisantika.springbootbankaccount.domain.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
public interface TransactionsService extends CrudRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByDateBetweenAndType(Date StartOfDay, Date endOfDay, int type);

}
