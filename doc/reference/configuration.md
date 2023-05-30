# 服务启动
- nacos 启动
- seata 启动
```shell
sh startup.sh -m standalone
sh ./seata-server.sh -p 8091 -h 127.0.0.1 -m file
```
- 如果你想节省点时间, 你可以考虑将组件的启动命令封装在一个shell脚本里面, 然后注册到系统环境变量中, 这样你就可以在任何地方启动你的组件了.
需要注意的是, 脚本中的启动命令本身使用的是绝对路径, 所以你需要根据自己的实际情况修改脚本中的启动命令.
[示例脚本](https://github.com/leejiliang/seata-practise/blob/main/doc/reference/uk.sh)
# Seata配置

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
# Nacos配置