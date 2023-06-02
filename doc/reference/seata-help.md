# SEATA的快速开始
## 添加依赖
添加seata的依赖有三种选择
1. seata-all 
    原始的依赖方式，包含了所有的依赖，但是不推荐使用, 需要手动注册TM, RM
2. seata-spring-boot-starter
    包含1, 但是不需要手动注册TM, RM, SpringBoot的自动配置机制, 自动注册了TM, RM, 适用于单体项目多数据源的场景.
3. seata-spring-cloud-starter
    包含2, 但是不需要手动注册TM, RM, SpringCloud的自动配置机制, 自动注册了TM, RM, 适用于微服务的场景.
## 配置文件
```yaml
seata:
  service:
    vgroup-mapping:
      default_tx_group: default ## default_tx_group为事务组名称，需要与服务端配置文件中的事务组名称保持一致, default为seata-server的集群名称, 单机模式下可以随意填写, 需要在group-list中指明服务地址.
    grouplist:
      default: 127.0.0.1:8091 ## default 为上面的default_tx_group的值, value为集群的地址
  tx-service-group: default_tx_group ## 分组名称, 需要和vgroup-mapping中的default_tx_group保持一致.
  registry:
    nacos:
      server-addr: localhost:8848 ## nacos的地址
      application: seata-server
      group: SEATA_GROUP 
```
### 注意点：TM（事务管理器）如何通过nacos注册中心定位TC（事务协调者）服务
- 通过server-addr+namespace+vgroup-mapping.{cluster}来定位seata-server服务集群
- 获取事务分组(服务启动时加载配置) spring/springboot可配置在yml、properties中，对应值"default_tx_group"即为事务分组名，可以自定义
- 查找TC集群名 拿到事务分组名"default_tx_group"拼接成"service.vgroupMapping.default_tx_group"从配置中心查找到TC集群名clusterName为"default"
- 查找TC服务 根据serverAddr和namespace以及clusterName在注册中心找到真实TC服务列表
- 注：serverAddr和namespace与Server端一致，clusterName与Server端cluster一致
- seata官方解释链接（文档结尾处）<https://seata.io/zh-cn/docs/user/txgroup/transaction-group.html>

## 编写代码
在需要开启分布式事务的方法上添加@GlobalTransactional注解
```java
    @SneakyThrows
    @GlobalTransactional
    public Long createOrder(TOrder order) {
        var xid = RootContext.getXID();
        try {
            Long payRecordId = acctClient.pay(order.getUserId(), order.getAmount(), xid);
            order.setPayRecordId(payRecordId);
//            TOrder tOrder = orderRepository.save(order);
            int i = 1 / 0;
            return 0l;
        } catch (Exception e) {
            GlobalTransactionContext.reload(xid).rollback();
            throw e;
        }
    }
```