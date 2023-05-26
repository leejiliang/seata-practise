package com.seata.acct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class UkAcctApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkAcctApplication.class, args);
    }

    @GetMapping("/")
    public String helloUk() {
        return "hello acct service";
    }
}
