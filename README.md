# seata-practise
seata 学习项目
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
[架构图](https://image-blog-lee.oss-cn-hangzhou.aliyuncs.com/uPic/g3sT6R.jpg)
