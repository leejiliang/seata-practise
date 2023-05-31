## mybatis-plus多数据源快速集成及使用教程
[mybatis-plus多数据源官方参考链接](https://www.baomidou.com/pages/a61e1b/)

### 1.0 依赖引入
以使用druid连接池为例
``` xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
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
这里采用了yml配置方式，编码方式请自定义 。这里配置了2主2从
``` yaml
spring:
  datasource:
    dynamic:
      druid:
        # 初始连接数
        initialSize: 5
        # 最小连接池数量
        minIdle: 10
        # 最大连接池数量
        maxActive: 20
        # 配置获取连接等待超时的时间
        maxWait: 60000
        # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        timeBetweenEvictionRunsMillis: 60000
        # 配置一个连接在池中最小生存的时间，单位是毫秒
        minEvictableIdleTimeMillis: 300000
        # 配置一个连接在池中最大生存的时间，单位是毫秒
        maxEvictableIdleTimeMillis: 900000
        # 配置检测连接是否有效
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
      primary: master # 设置默认的数据源或者数据源组,默认值即为master。当有多个master_{}时会随机master。也可以指定固定的某个master
      strict: false # 严格匹配数据源,默认false. true未匹配到指定数据源时抛异常,false使用默认数据源
      datasource:
        master_1:
          url: jdbc:mysql://localhost:3307/seata_uk?serverTimezone=Asia/Shanghai
          username: uk
          password: 123456
          driver-class-name: com.mysql.cj.jdbc.Driver
        master_2:
          url: jdbc:mysql://localhost:3307/seata_uk2?serverTimezone=Asia/Shanghai
          username: uk
          password: 123456
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave_1:
          url: jdbc:mysql://localhost:3307/seata_uk_s1?serverTimezone=Asia/Shanghai
          username: uk
          password: 123456
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave_2:
          url: jdbc:mysql://localhost:3307/seata_uk_s2?serverTimezone=Asia/Shanghai
          username: uk
          password: 123456
          driver-class-name: com.mysql.cj.jdbc.Driver
      # 自定义负载均衡策略。slave组下有；多个个数据源，当用户使用 slave 切换数据源时会使用负载均衡算法。系统自带了两个负载均衡算法 LoadBalanceDynamicDataSourceStrategy 轮询,是默认的。 RandomDynamicDataSourceStrategy 随机的。
      strategy: com.baomidou.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy
```

### 1.2 自定义mybatis#SqlSessionFactory配置
> 详情见配置类：`com.seata.warehouse.config.MybatisPlusConfig.class`


##### 使用注意
- @MapperScan 注解定义自动扫描待mapper包
- @EnableTransactionManagement 开启事务注解扫描，可忽略
- 可以自定义配置事务管理器等信息

#### 1.3 测试代码
>详情见测试类：`com.seata.warehouse.PayRecordServiceTest`

#### 1.4 使用问题
###### 1.4.1 内部方法调用会导致切换数据源失效
> 原因：内部方法直接调用，不会走aop代理，导致切换数据源失败
> 
> 解决办法：1. 注入当前类；2. 获取当前类的代理对象调用
> 
> 使用前需要开启：@EnableAspectJAutoProxy(exposeProxy = true)
```java
@Compent
public class Demo {

    // 错误示例
    public void select1(){
        select2();
        select3();
    }

    // 正确示例
    public void select1(){
        Demo demo = (Demo) AopContext.currentProxy();
        demo.select2();
        demo.select3();
    }

    @DS("slave_1")
    public void select2(){

    }
    @DS("slave_2")
    public void select3(){

    }
}
```

##### 1.4.2 事务注解导致切换数据源失败
原因：spring检测到如果开启了事务，会将Connection缓存起来，然后在事务再执行第二条sql时候，继续使用之前缓存好的Connection

解决办法：
1. 可以把这个查询方法排除掉在这个事务内，我们可以利用spring事务隔离机制排除他，使用方法，在对应方法上加上
    
   `//当前方法不会运行在事务内，如果在事务内，则将事务挂起`
   `@Transactional(propagation = Propagation.NOT_SUPPORTED)`
2. 异步执行对应的方法
3. [网络参考链接](https://blog.51cto.com/u_15501718/5103320)
