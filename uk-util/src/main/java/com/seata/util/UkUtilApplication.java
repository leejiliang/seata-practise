package com.seata.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class UkUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(UkUtilApplication.class, args);
    }



}
