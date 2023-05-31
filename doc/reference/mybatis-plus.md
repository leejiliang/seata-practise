## mybatis-plus快速集成及使用教程

### 1.0 依赖引入
以使用druid连接池为例
``` xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
    </dependency>
    <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
    </dependency>
    <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
    </dependency>
```

### 1.1 数据源配置
这里采用了yml配置方式，编码方式请自定义
``` yaml
spring: 
  datasource:
    username: uk
    password: 123456
    host: localhost
    port: 3307
    url: jdbc:mysql://localhost:3306/seata_uk
```

### 1.2 自定义mybatis#SqlSessionFactory配置
> 详情见配置类：`com.seata.order.config.MybatisPlusConfig.class`


##### 使用注意
- @MapperScan 注解定义自动扫描待mapper包
- @EnableTransactionManagement 开启事务注解扫描，可忽略
- 可以自定义配置事务管理器等信息

#### 1.3 测试验证
>详情见测试类：`com.seata.order.OrderMapperTest`

