# 企业级订单履约放行治理

订单进入履约前统一校验反欺诈、出口管制、付款授权、地址、库存、价格、信用和人工例外审批。

`POST /api/enterprise/oms/order-release` 返回 `RELEASE / APPROVAL_REQUIRED / HOLD` 决策。生产环境应关联风控快照、库存 ATP、授权矩阵和审计日志。
