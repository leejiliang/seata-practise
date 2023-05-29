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

