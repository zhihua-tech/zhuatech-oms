# ZhuaTech OMS API

Copyright © 2026 上海如静知华信息科技有限公司。

## 约定

- 默认前缀：`/api`
- 除登录外均使用 `Authorization: Bearer <token>`
- 响应结构：`{ "success": true, "message": "OK", "data": ... }`
- 时间采用 ISO 8601，服务端时区为 `Asia/Shanghai`

## 认证

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/auth/login` | 登录并获取 JWT |
| GET | `/auth/me` | 获取当前用户 |

## OMS 业务接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/oms/dashboard` | 今日 GMV、订单、履约、售后和异常指标 |
| GET | `/oms/orders` | 按下单时间倒序查询订单 |
| POST | `/oms/orders` | 创建人工订单 |
| PATCH | `/oms/orders/{id}/status` | 更新订单状态并记录事件 |
| GET | `/oms/shipments` | 查询发运任务和物流信息 |
| GET | `/oms/after-sales` | 查询退款、退货和换货申请 |
| POST | `/oms/after-sales` | 创建售后申请 |
| GET | `/oms/channels` | 查询渠道连接及同步状态 |
| GET | `/oms/events` | 查询最近订单事件 |

## 订单状态

`PENDING_PAYMENT`、`PAID`、`ALLOCATING`、`READY_TO_SHIP`、`SHIPPED`、`COMPLETED`、`CANCELLED`、`AFTER_SALE`

## 权限

- 读取接口：登录用户
- 人工建单：`ADMIN`、`MANAGER`、`SALES`
- 状态更新：`ADMIN`、`MANAGER`、`SALES`、`WAREHOUSE`
- 创建售后：`ADMIN`、`MANAGER`、`SALES`

生产集成应补充幂等键、签名校验、审计日志、限流及细粒度数据权限。
