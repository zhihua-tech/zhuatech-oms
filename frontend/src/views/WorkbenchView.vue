<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
const groups = [
  { title:'订单处理', note:'归集与审单', items:[['订单中心','orders-o','/orders'],['渠道订单','shop-o','/channels'],['订单规则','setting-o','/rules'],['异常订单','warning-o','/orders']] },
  { title:'履约协同', note:'从分仓到签收', items:[['智能分仓','cluster-o','/fulfillment'],['拣货打包','qr','/fulfillment'],['物流追踪','logistics','/fulfillment'],['履约预警','bulb-o','/fulfillment']] },
  { title:'服务与分析', note:'售后闭环', items:[['售后中心','after-sale','/after-sales'],['订单分析','bar-chart-o','/analytics'],['对账任务','balance-list-o',''],['数据导出','down','']] }
]
</script>
<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">运营工作台</h1><div class="page-subtitle">围绕订单组织每一次协同</div></div><span class="head-action"><van-icon name="scan" size="20" /></span></div>
    <section class="flow-card"><header><span>订单履约主链路</span><b>96.8% 准时</b></header><div class="flow"><i class="done"></i><em></em><i class="done"></i><em></em><i class="active"></i><em></em><i></i><em></em><i></i></div><footer><span>归集</span><span>审单</span><span>分仓</span><span>发运</span><span>签收</span></footer></section>
    <section v-for="(group,index) in groups" :key="group.title" class="card group-card"><header><div><span>{{ index+1 }}</span><b>{{ group.title }}</b></div><small>{{ group.note }}</small></header><div class="module-grid"><component :is="item[2] ? 'router-link' : 'div'" v-for="item in group.items" :key="item[0]" :to="item[2]" class="module-item" :class="{disabled:!item[2]}"><span class="module-icon" :class="index===1?'cyan':index===2?'orange':''"><van-icon :name="item[1]" /></span><span>{{ item[0] }}</span></component></div></section>
    <div class="version">ZhuaTech OMS Community · v1.0.0</div>
  </div>
</template>
<style scoped>
.flow-card{padding:18px;border-radius:21px;color:#fff;background:linear-gradient(135deg,#171d49,#4f56cf)}.flow-card header{display:flex;justify-content:space-between;color:#c5caf8;font-size:10px}.flow-card header b{color:#91ebdd}.flow{display:grid;grid-template-columns:12px 1fr 12px 1fr 12px 1fr 12px 1fr 12px;align-items:center;margin:20px 5px 7px}.flow i{width:12px;height:12px;border:2px solid #777fb8;border-radius:50%}.flow i.done{border-color:#71ddd0;background:#71ddd0}.flow i.active{border-color:#f3aa5c;box-shadow:0 0 0 4px rgba(243,170,92,.18)}.flow em{height:2px;background:#636bb0}.flow-card footer{display:flex;justify-content:space-between;color:#aeb5e9;font-size:8px}.group-card{padding:17px 14px 19px}.group-card header{display:flex;justify-content:space-between;align-items:center;margin-bottom:17px}.group-card header>div{display:flex;align-items:center;gap:8px}.group-card header span{width:21px;height:21px;display:grid;place-items:center;border-radius:7px;background:#eceeff;color:#5b5fe9;font-size:9px}.group-card header b{font-size:13px}.group-card header small{color:#969daf;font-size:9px}.module-icon.cyan{color:#199d8a;background:#e3f8f4}.module-icon.orange{color:#d97820;background:#fff0e2}.disabled{opacity:.42;pointer-events:none}.version{text-align:center;color:#a0a6b6;font-size:9px;margin:27px 0}
</style>
