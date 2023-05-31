package com.seata.acct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
public class UkAcctApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkAcctApplication.class, args);
    }

    @GetMapping("/")
    public String helloUk() {
        return "hello acct service";
    }

    @GetMapping("/my-health-check")
    public ResponseEntity<String> myCustomCheck() {
        String message = "Testing acct health check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
