/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
const delay = value => new Promise(resolve => setTimeout(() => resolve(value), 90))

const orders = [
  { id:1, orderNo:'OMS202607260001', externalOrderNo:'TM202607261892', channelName:'天猫旗舰店', customerName:'上海云端科技有限公司', customerPhone:'138****1028', itemSummary:'工业边缘网关 Pro × 3', itemCount:3, totalAmount:8040, paidAmount:8040, status:'READY_TO_SHIP', warehouseName:'上海一号仓', orderedAt:'2026-07-26T14:36:00', promisedShipAt:'2026-07-26T22:00:00' },
  { id:2, orderNo:'OMS202607260002', externalOrderNo:'JD202607265721', channelName:'京东自营店', customerName:'王先生', customerPhone:'139****6612', itemSummary:'温湿度传感器 × 12；通信模组 × 2', itemCount:14, totalAmount:2596, paidAmount:2596, status:'ALLOCATING', warehouseName:'苏州智能仓', orderedAt:'2026-07-26T14:12:00', promisedShipAt:'2026-07-27T02:00:00' },
  { id:3, orderNo:'OMS202607260003', externalOrderNo:'WEB202607260087', channelName:'品牌官网商城', customerName:'星云数科（上海）有限公司', customerPhone:'136****3506', itemSummary:'企业协同软件授权 × 8', itemCount:8, totalAmount:70400, paidAmount:70400, status:'PAID', warehouseName:'虚拟商品仓', orderedAt:'2026-07-26T13:42:00', promisedShipAt:'2026-07-26T18:00:00' },
  { id:4, orderNo:'OMS202607250016', externalOrderNo:'WEB202607250611', channelName:'品牌官网商城', customerName:'海岳智能制造有限公司', customerPhone:'137****8860', itemSummary:'工业边缘网关 Pro × 20', itemCount:20, totalAmount:53600, paidAmount:53600, status:'SHIPPED', warehouseName:'上海一号仓', carrierName:'顺丰速运', trackingNo:'SF14202607250016', orderedAt:'2026-07-25T16:20:00', promisedShipAt:'2026-07-26T12:00:00' },
  { id:5, orderNo:'OMS202607250009', externalOrderNo:'OFF202607250039', channelName:'华东线下渠道', customerName:'嘉禾机电设备有限公司', customerPhone:'135****7791', itemSummary:'数字化实施服务包 × 1', itemCount:1, totalAmount:26000, paidAmount:26000, status:'AFTER_SALE', warehouseName:'服务交付中心', orderedAt:'2026-07-25T10:08:00', promisedShipAt:'2026-07-25T18:00:00' }
]

const shipments = [
  { id:1, shipmentNo:'SHP20260726001', orderNo:'OMS202607260001', warehouseName:'上海一号仓', carrierName:'顺丰速运', trackingNo:null, status:'PACKED', itemCount:3, shippedAt:null, expectedDeliveryAt:'2026-07-27T18:00:00' },
  { id:2, shipmentNo:'SHP20260725016', orderNo:'OMS202607250016', warehouseName:'上海一号仓', carrierName:'顺丰速运', trackingNo:'SF14202607250016', status:'SHIPPED', itemCount:20, shippedAt:'2026-07-26T12:05:00', expectedDeliveryAt:'2026-07-27T18:00:00' },
  { id:3, shipmentNo:'SHP20260726002', orderNo:'OMS202607260002', warehouseName:'苏州智能仓', carrierName:'京东物流', trackingNo:null, status:'EXCEPTION', itemCount:14, shippedAt:null, expectedDeliveryAt:'2026-07-28T18:00:00' }
]

const afterSales = [
  { id:1, requestNo:'AS20260726001', orderNo:'OMS202607250009', customerName:'嘉禾机电设备有限公司', type:'REFUND_ONLY', reason:'服务排期调整，申请部分退款', amount:6800, status:'PENDING', requestedAt:'2026-07-26T14:24:00', handlerName:'林运营' },
  { id:2, requestNo:'AS20260725008', orderNo:'OMS202607250016', customerName:'海岳智能制造有限公司', type:'EXCHANGE', reason:'其中一台设备外壳磕碰', amount:2680, status:'PROCESSING', requestedAt:'2026-07-26T10:00:00', handlerName:'周专员' },
  { id:3, requestNo:'AS20260724003', orderNo:'OMS202607240031', customerName:'张女士', type:'RETURN_REFUND', reason:'型号选择错误', amount:1680, status:'COMPLETED', requestedAt:'2026-07-24T16:10:00', handlerName:'周专员' }
]

const channels = [
  { id:1, code:'SELF', name:'品牌官网商城', type:'SELF_OPERATED', status:'ENABLED', syncStatus:'NORMAL', todayOrders:126, todayAmount:186420, lastSyncAt:'2026-07-26T14:58:00' },
  { id:2, code:'TMALL', name:'天猫旗舰店', type:'MARKETPLACE', status:'ENABLED', syncStatus:'NORMAL', todayOrders:98, todayAmount:132860, lastSyncAt:'2026-07-26T14:57:00' },
  { id:3, code:'JD', name:'京东自营店', type:'MARKETPLACE', status:'ENABLED', syncStatus:'WARNING', todayOrders:67, todayAmount:96480, lastSyncAt:'2026-07-26T14:42:00' },
  { id:4, code:'OFFLINE', name:'华东线下渠道', type:'DISTRIBUTOR', status:'ENABLED', syncStatus:'NORMAL', todayOrders:23, todayAmount:72800, lastSyncAt:'2026-07-26T14:55:00' }
]

const events = [
  { id:1, orderNo:'OMS202607260001', eventType:'FULFILLMENT', title:'拣货完成', description:'上海一号仓已完成 3 件商品拣货', operatorName:'仓储机器人', occurredAt:'2026-07-26T14:52:00' },
  { id:2, orderNo:'OMS202607260002', eventType:'EXCEPTION', title:'库存分配异常', description:'通信模组可用库存不足，等待跨仓调拨', operatorName:'OMS 规则引擎', occurredAt:'2026-07-26T14:42:00' },
  { id:3, orderNo:'OMS202607260003', eventType:'PAYMENT', title:'支付确认', description:'企业网银支付已核销，等待虚拟权益发放', operatorName:'支付中心', occurredAt:'2026-07-26T14:29:00' },
  { id:4, orderNo:'OMS202607250016', eventType:'LOGISTICS', title:'包裹已揽收', description:'顺丰速运已揽收，预计明日送达', operatorName:'物流回传', occurredAt:'2026-07-26T12:05:00' }
]

export const mockApi = {
  login: form => {
    if (!['demo', 'admin', 'operator'].includes(form.username)) return Promise.reject(new Error('账号不存在'))
    const user = form.username === 'admin'
      ? { id:1, username:'admin', fullName:'系统管理员', role:'ADMIN', department:'数字化中心' }
      : form.username === 'operator'
        ? { id:3, username:'operator', fullName:'周专员', role:'SALES', department:'履约运营组' }
        : { id:2, username:'demo', fullName:'林运营', role:'MANAGER', department:'订单运营中心' }
    return delay({ token:'zhuatech-oms-demo-token', user })
  },
  me: () => delay({ id:2, username:'demo', fullName:'林运营', role:'MANAGER', department:'订单运营中心' }),
  dashboard: () => delay({ todayGmv:496560, todayOrders:314, pendingAllocation:18, pendingShipment:27, pendingAfterSales:6, channelWarnings:1, shippingExceptions:1, fulfillmentRate:96.8 }),
  orders: () => delay(orders),
  shipments: () => delay(shipments),
  afterSales: () => delay(afterSales),
  channels: () => delay(channels),
  events: () => delay(events)
}
