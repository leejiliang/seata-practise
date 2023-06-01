package com.seata.warehouse.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * @auther: jia.you
 * @date: 2023/05/30/10:15 AM
 * @version 1.0
 * @description:
 */
//@Configuration
//@MapperScan(basePackages = "com.seata.warehouse.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@RequiredArgsConstructor
@Deprecated
public class MybatisPlusDiscardConfig {
    private final MultiDataSourceProperties dataSourceConfiguration;

    @Bean("m1DataSourceConfiguration")
    public DataSourceProperties m1DataSourceProperty() {
        var dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(dataSourceConfiguration.getM1Url());
        dataSourceProperties.setUsername(dataSourceConfiguration.getM1Username());
        dataSourceProperties.setPassword(dataSourceConfiguration.getM1Password());
        dataSourceProperties.setDriverClassName(dataSourceConfiguration.getM1DriverClassName());
        return dataSourceProperties;
    }

    @Bean("m2DataSourceConfiguration")
    public DataSourceProperties m2DataSourceProperty() {
        var dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(dataSourceConfiguration.getM2Url());
        dataSourceProperties.setUsername(dataSourceConfiguration.getM2Username());
        dataSourceProperties.setPassword(dataSourceConfiguration.getM2Password());
        dataSourceProperties.setDriverClassName(dataSourceConfiguration.getM2DriverClassName());
        return dataSourceProperties;
    }

    @Bean("s1DataSourceConfiguration")
    public DataSourceProperties s1DataSourceProperty() {
        var dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(dataSourceConfiguration.getS1Url());
        dataSourceProperties.setUsername(dataSourceConfiguration.getS1Username());
        dataSourceProperties.setPassword(dataSourceConfiguration.getS1Password());
        dataSourceProperties.setDriverClassName(dataSourceConfiguration.getS1DriverClassName());
        return dataSourceProperties;
    }

    @Bean("s2DataSourceConfiguration")
    public DataSourceProperties s2DataSourceProperty() {
        var dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(dataSourceConfiguration.getS2Url());
        dataSourceProperties.setUsername(dataSourceConfiguration.getS2Username());
        dataSourceProperties.setPassword(dataSourceConfiguration.getS2Password());
        dataSourceProperties.setDriverClassName(dataSourceConfiguration.getS2DriverClassName());
        return dataSourceProperties;
    }

    @Bean("originMaster1")
    public DataSource dataSourceMaster(@Qualifier("m1DataSourceConfiguration") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Bean("originMaster2")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master2")
    public DataSource dataSourceMaster2(@Qualifier("m2DataSourceConfiguration") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Bean("originSlave1")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave1")
    public DataSource dataSourceSlave1(@Qualifier("s1DataSourceConfiguration") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Bean("originSlave2")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave2")
    public DataSource dataSourceSlave2(@Qualifier("s2DataSourceConfiguration") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Primary
    @Bean(name = "master1")
    public DataSourceProxy masterDataSourceProxy(@Qualifier("originMaster1") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "master2")
    public DataSourceProxy master2DataSourceProxy(@Qualifier("originMaster2") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "slave1")
    public DataSourceProxy slave1DataSourceProxy(@Qualifier("originSlave1") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "slave2")
    public DataSourceProxy slave2DataSourceProxy(@Qualifier("originSlave2") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("master1") DataSource dataSourceMaster1,
                                        @Qualifier("master2") DataSource dataSourceMaster2,
                                        @Qualifier("slave1") DataSource dataSourceSlave1,
                                        @Qualifier("slave2") DataSource dataSourceSlave2) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        dynamicRoutingDataSource.addDataSource("master1", dataSourceMaster1);
        dynamicRoutingDataSource.addDataSource("master2",dataSourceMaster2);
        dynamicRoutingDataSource.addDataSource("slave1",dataSourceSlave1);
        dynamicRoutingDataSource.addDataSource("slave2",dataSourceSlave2);
        dynamicRoutingDataSource.setPrimary("master1");
        dynamicRoutingDataSource.setSeata(true);

        return dynamicRoutingDataSource;
    }

    /**
     * 解决引入seata后@Transactional注解失效的问题
     */
    @Bean("txManager")
    public DataSourceTransactionManager txManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource){
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        // mapper.xml别名映射
        sqlSessionFactoryBean.setTypeAliasesPackage("com.seata.warehouse.entity");
        String[] mapperLocations = new String[1];
        mapperLocations[0] = "classpath*:/mapper/*Mapper.xml";
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(mapperLocations));

        // 设置分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        sqlSessionFactoryBean.setPlugins(interceptor);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); //设置驼峰映射
//        configuration.setLogImpl(StdOutImpl.class); //设置打印sql
        sqlSessionFactoryBean.setConfiguration(configuration);

        // 设置全局id生成策略
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);

        return sqlSessionFactoryBean;
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}
