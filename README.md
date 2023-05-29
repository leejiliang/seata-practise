# seata-practise
seata 学习项目
# TODO LIST
1. [x] 替换服务发现组件为nacos
2. [x] 使用组件seata, 测试AT模式
3. [ ] 引入分库分表, 测试AT模式
4. [ ] 引入分布式事务, 测试XA模式
5. [ ] 引入分布式事务, 测试SAGA模式
6. [ ] 引入分布式事务, 测试TCC模式
7. [ ] 引入Dubbo, 替换feign
8. [ ] 持久层使用Mybatis-Plus
# 参考文档地址
- 源码地址
https://github.com/seata/seata
- 官方文档地址
https://seata.io/zh-cn/docs/overview/what-is-seata.html
- 完整视屏教程推荐(B站-诸葛)
https://github.com/leejiliang/seata-practise.git
# 快速开始
---
## 用例
用户购买商品的业务逻辑。整个业务逻辑由3个微服务提供支持：

- 仓储服务：对给定的商品扣除仓储数量。
- 订单服务：根据采购需求创建订单。
- 帐户服务：从用户帐户中扣除余额。

---
## 架构图
---
![架构图](https://image-blog-lee.oss-cn-hangzhou.aliyuncs.com/uPic/g3sT6R.jpg)

# 相关SQL
```sql
## 创建schema
create schema seata_uk default character set utf8 collate utf8_general_ci;
## 创建用户
DROP USER uk;
create user uk@'%' identified by '123456';
## 授权
grant all on seata_uk.* to uk@'%' with grant option;
flush privileges;
```

# 组件版本
1. [spring cloud version to spring boot](https://github.com/spring-cloud/spring-cloud-release/wiki/Supported-Versions)
2. [spring cloud alibaba version to spring cloud](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

# 核心配置
- nacos 启动
- seata 启动
```shell
sh startup.sh -m standalone
sh ./seata-server.sh -p 8091 -h 127.0.0.1 -m file
```
- seata 服务配置
```yaml
server:
  port: 7091

spring:
  application:
    name: seata-server

logging:
  config: classpath:logback-spring.xml
  file:
    path: ${user.home}/logs/seata

console:
  user:
    username: seata
    password: seata

seata:
  config:
    type: file
  registry:
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace:
      group: SEATA_GROUP
      username:
      password:
      context-path:
      ##if use MSE Nacos with auth, mutex with username/password attribute
      #access-key:
      #secret-key:
      data-id: seataServer.properties
  store:
    mode: db
    db:
      datasource: druid
      db-type: mysql
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/seata?rewriteBatchedStatements=true
      user: uk
      password: 123456
      min-conn: 10
      max-conn: 100
      global-table: global_table
      branch-table: branch_table
      lock-table: lock_table
      distributed-lock-table: distributed_lock
      query-limit: 1000
      max-wait: 5000
#  server:
#    service-port: 8091 #If not configured, the default is '${server.port} + 1000'
  security:
    secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
    tokenValidityInMilliseconds: 1800000
    ignore:
      urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
```