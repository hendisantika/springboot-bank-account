package com.hendisantika.springbootbankaccount.service;

import com.hendisantika.springbootbankaccount.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-bank-account
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-17
 * Time: 17:44
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountServiceTests {

    @Autowired
    AccountService accountService;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindOne() {
        this.entityManager.persist(new Account(400.0));
        this.accountService.findAll();
        Account account = this.accountService.findById(2L).get(); // ID 2 because default account 1 is created whenever the program runs
        // see Application.init
//        assertThat(account.getAmount()).isEqualTo(400.0);

    }

}