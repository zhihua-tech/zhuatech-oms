# 拆单履约治理

`POST /api/enterprise/oms/split-fulfillment` 用于订单分配和锁库前的拆单方案审查。

- 对齐支付、反欺诈、库存节点、承诺时效和客户单包裹约束。
- 检查受限商品节点资质及跨境报关单证。
- 计算可用节点数，识别运费后毛利例外和客户通知任务。

返回 `RELEASE / REVIEW / BLOCKED`，保证拆单可执行且可解释。
