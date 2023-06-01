package com.seata.warehouse.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 */
//@Configuration
@Data
@Deprecated
public class MultiDataSourceProperties {
    @Value("${spring.datasource.dynamic.datasource.master_1.url}")
    private String m1Url;
    @Value("${spring.datasource.dynamic.datasource.master_1.username}")
    private String m1Username;
    @Value("${spring.datasource.dynamic.datasource.master_1.password}")
    private String m1Password;
    @Value("${spring.datasource.dynamic.datasource.master_1.driver-class-name}")
    private String m1DriverClassName;

    @Value("${spring.datasource.dynamic.datasource.master_2.url}")
    private String m2Url;
    @Value("${spring.datasource.dynamic.datasource.master_2.username}")
    private String m2Username;
    @Value("${spring.datasource.dynamic.datasource.master_2.password}")
    private String m2Password;
    @Value("${spring.datasource.dynamic.datasource.master_2.driver-class-name}")
    private String m2DriverClassName;

    @Value("${spring.datasource.dynamic.datasource.slave_1.url}")
    private String s1Url;
    @Value("${spring.datasource.dynamic.datasource.slave_1.username}")
    private String s1Username;
    @Value("${spring.datasource.dynamic.datasource.slave_1.password}")
    private String s1Password;
    @Value("${spring.datasource.dynamic.datasource.slave_1.driver-class-name}")
    private String s1DriverClassName;

    @Value("${spring.datasource.dynamic.datasource.slave_2.url}")
    private String s2Url;
    @Value("${spring.datasource.dynamic.datasource.slave_2.username}")
    private String s2Username;
    @Value("${spring.datasource.dynamic.datasource.slave_2.password}")
    private String s2Password;
    @Value("${spring.datasource.dynamic.datasource.slave_2.driver-class-name}")
    private String s2DriverClassName;
}
