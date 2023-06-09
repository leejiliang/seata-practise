package com.seata.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
public class UkWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkWarehouseApplication.class, args);
    }

    @GetMapping("/")
    public String helloUk() {
        return "hello warehouse service";
    }

    @GetMapping("/my-health-check")
    public ResponseEntity<String> myCustomCheck() {
        String message = "Testing warehouse health check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
