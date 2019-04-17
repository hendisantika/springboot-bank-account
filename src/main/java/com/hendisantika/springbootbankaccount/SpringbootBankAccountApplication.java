package com.hendisantika.springbootbankaccount;

import com.hendisantika.springbootbankaccount.domain.Account;
import com.hendisantika.springbootbankaccount.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBankAccountApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(AccountService accountService) {
        return (args) -> {
            // create account
            accountService.save(new Account(0));
        };
    }

}
