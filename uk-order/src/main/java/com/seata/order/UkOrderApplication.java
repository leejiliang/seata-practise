package com.seata.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UkOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkOrderApplication.class, args);
    }


    @GetMapping("/")
    public String helloUk() {
        return "hello order service";
    }
}
