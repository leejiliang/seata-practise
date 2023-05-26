package com.seata.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class UkWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkWarehouseApplication.class, args);
    }

    @GetMapping("/")
    public String helloUk() {
        return "hello warehouse service";
    }
}
