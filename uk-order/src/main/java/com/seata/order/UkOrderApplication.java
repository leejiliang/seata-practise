package com.seata.order;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = SeataFeignClientAutoConfiguration.class)
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class UkOrderApplication {

    @Autowired
    private ResourceLoader resourceLoader;

    public static void main(String[] args) {
        SpringApplication.run(UkOrderApplication.class, args);
    }

    @GetMapping("/")
    public String helloUk() {
        return "hello order service";
    }

    @GetMapping("/my-health-check")
    public ResponseEntity<String> myCustomCheck() {
        String message = "Testing order health check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/init-config")
    public void initConfig() throws IOException {
        var resource = resourceLoader.getResource("classpath:config.json");
        Map<String,Object> result =
            new ObjectMapper().readValue(resource.getFile(), HashMap.class);
        RestTemplate restTemplate = new RestTemplate();
        result.forEach((k, v) -> {
            var headers = new HttpHeaders();
            headers.put("content-type", List.of("application/json","charset=UTF-8"));
            var httpEntity = new HttpEntity<>(v, headers);
            restTemplate.exchange("http://localhost:8500/v1/kv/seata.properties/" + k, HttpMethod.PUT, httpEntity, String.class);
        });
        System.out.println(result);
    }
}
