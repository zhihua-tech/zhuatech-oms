<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { onMounted, ref } from 'vue'
import { api } from '../api/oms'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()
const dashboard = ref({ todayGmv:0, todayOrders:0, pendingAllocation:0, pendingShipment:0, pendingAfterSales:0, channelWarnings:0, shippingExceptions:0, fulfillmentRate:0 })
const channels = ref([])
const events = ref([])
const modules = [
  ['订单中心','orders-o','/orders','violet'], ['履约发运','logistics','/fulfillment','cyan'],
  ['售后中心','after-sale','/after-sales','orange'], ['渠道管理','shop-o','/channels','blue'],
  ['订单分析','bar-chart-o','/analytics','violet'], ['订单规则','setting-o','/rules','cyan'],
  ['运营工作台','cluster-o','/workbench','blue'], ['导出任务','down','', 'muted']
]
const money = value => Number(value || 0).toLocaleString('zh-CN')
const maxChannel = () => Math.max(...channels.value.map(item => item.todayAmount), 1)

onMounted(async () => {
  [dashboard.value, channels.value, events.value] = await Promise.all([api.dashboard(), api.channels(), api.events()])
})
</script>

<template>
  <div class="page safe-top home-page">
    <header class="topbar"><div class="identity"><span>ZH</span><div><b>知华 OMS</b><small>{{ auth.user?.department }}</small></div></div><div class="signal"><i></i>订单流在线</div></header>
    <section class="hero">
      <div class="hero-label">今日全渠道 GMV <span>实时</span></div>
      <strong><small>¥</small>{{ money(dashboard.todayGmv) }}</strong>
      <div class="hero-foot"><div><b>{{ dashboard.todayOrders }}</b><span>新增订单</span></div><div><b>{{ dashboard.fulfillmentRate }}%</b><span>履约及时率</span></div><div><b>{{ dashboard.pendingShipment }}</b><span>待发货</span></div></div>
      <i class="glow"></i>
    </section>
    <section class="kpis">
      <div><span class="dot purple"></span><p>待分仓</p><b>{{ dashboard.pendingAllocation }}</b></div>
      <div><span class="dot orange"></span><p>售后待办</p><b>{{ dashboard.pendingAfterSales }}</b></div>
      <div><span class="dot red"></span><p>物流异常</p><b>{{ dashboard.shippingExceptions }}</b></div>
      <div><span class="dot cyan"></span><p>渠道预警</p><b>{{ dashboard.channelWarnings }}</b></div>
    </section>
    <h2 class="section-title">订单运营 <small>一张订单贯穿全链路</small></h2>
    <section class="card module-grid">
      <component :is="item[2] ? 'router-link' : 'div'" v-for="item in modules" :key="item[0]" :to="item[2]" class="module-item" :class="{ disabled:!item[2] }">
        <span class="module-icon" :class="item[3]"><van-icon :name="item[1]" /></span><span>{{ item[0] }}</span>
      </component>
    </section>
    <div class="section-title"><h2>渠道贡献</h2><router-link to="/channels">查看全部</router-link></div>
    <section class="card channel-card">
      <div v-for="channel in channels.slice(0,3)" :key="channel.id" class="channel-row">
        <span class="channel-logo">{{ channel.name.slice(0,1) }}</span><div class="channel-data"><div><b>{{ channel.name }}</b><span>¥{{ money(channel.todayAmount) }}</span></div><i><em :style="{ width:(channel.todayAmount/maxChannel()*100)+'%' }"></em></i></div>
      </div>
    </section>
    <div class="section-title"><h2>实时动态</h2><span>自动刷新</span></div>
    <section class="timeline">
      <div v-for="event in events.slice(0,3)" :key="event.id"><i :class="event.eventType.toLowerCase()"></i><section><b>{{ event.title }}</b><p>{{ event.description }}</p><small>{{ event.orderNo }} · {{ event.operatorName }}</small></section></div>
    </section>
  </div>
</template>

<style scoped>
.topbar{display:flex;align-items:center;justify-content:space-between;margin:1px 1px 17px}.identity{display:flex;align-items:center;gap:10px}.identity>span{width:39px;height:39px;display:grid;place-items:center;border-radius:13px;background:var(--ink);color:#9ff3e7;font-size:11px;font-weight:900}.identity div{display:flex;flex-direction:column}.identity b{font-size:15px}.identity small{margin-top:3px;color:var(--muted);font-size:9px}.signal{padding:7px 10px;border:1px solid var(--line);border-radius:999px;background:#fff;color:#6b7391;font-size:9px}.signal i{display:inline-block;width:6px;height:6px;margin-right:5px;border-radius:50%;background:#36c8af}.hero{position:relative;overflow:hidden;padding:21px 20px 18px;border-radius:25px;color:#fff;background:linear-gradient(135deg,#171d4f,#5058da 73%,#626cf2);box-shadow:0 16px 32px rgba(53,59,150,.25)}.hero-label{position:relative;z-index:2;color:#c6cbff;font-size:11px}.hero-label span{margin-left:6px;padding:3px 7px;border-radius:99px;background:rgba(151,244,228,.15);color:#9ff3e7;font-size:8px}.hero>strong{position:relative;z-index:2;display:block;margin:12px 0 18px;font-size:36px;letter-spacing:-1.2px}.hero>strong small{margin-right:4px;font-size:17px}.hero-foot{position:relative;z-index:2;display:grid;grid-template-columns:repeat(3,1fr)}.hero-foot div{display:flex;flex-direction:column;border-right:1px solid rgba(255,255,255,.16)}.hero-foot div+div{padding-left:14px}.hero-foot div:last-child{border:0}.hero-foot b{font-size:15px}.hero-foot span{margin-top:4px;color:#b8bff7;font-size:8px}.glow{position:absolute;width:180px;height:180px;right:-50px;top:-65px;border-radius:50%;background:radial-gradient(circle,rgba(145,235,221,.24),transparent 64%)}.kpis{display:grid;grid-template-columns:repeat(4,1fr);gap:8px;margin-top:12px}.kpis div{position:relative;padding:12px 8px;border:1px solid var(--line);border-radius:16px;background:#fff}.kpis p{margin:9px 0 4px;color:#7d859b;font-size:9px}.kpis b{font-size:18px;color:var(--ink)}.dot{display:block;width:7px;height:7px;border-radius:50%}.dot.purple{background:#6e70ed}.dot.orange{background:#f3a655}.dot.red{background:#ef6d6d}.dot.cyan{background:#37c5b0}.section-title h2{margin:0;font-size:17px}.section-title a,.section-title>span{color:#7f87a0;text-decoration:none;font-size:10px}.module-icon.violet{color:#5b5fe9;background:#ececff}.module-icon.cyan{color:#169d8b;background:#e5f8f5}.module-icon.orange{color:#dc791d;background:#fff1e3}.module-icon.blue{color:#3675ad;background:#eaf3fb}.module-icon.muted{color:#9ba2b6;background:#f0f2f6}.disabled{opacity:.45;pointer-events:none}.channel-card{padding:15px}.channel-row{display:flex;align-items:center;gap:11px;padding:9px 2px}.channel-logo{flex:none;width:34px;height:34px;display:grid;place-items:center;border-radius:11px;background:#eef0ff;color:#555bd5;font-size:12px;font-weight:800}.channel-data{flex:1}.channel-data>div{display:flex;justify-content:space-between}.channel-data b{font-size:11px}.channel-data span{color:#616987;font-size:10px}.channel-data>i{display:block;height:5px;margin-top:8px;overflow:hidden;border-radius:99px;background:#edf0f5}.channel-data em{display:block;height:100%;border-radius:99px;background:linear-gradient(90deg,#555bdb,#61cfc0)}.timeline{padding:2px 3px 5px}.timeline>div{display:flex;gap:12px;padding-bottom:16px}.timeline>div>i{flex:none;width:10px;height:10px;margin-top:3px;border:3px solid #aeb4f9;border-radius:50%;box-shadow:0 0 0 4px #eef0ff}.timeline>div>i.exception{border-color:#f0a04c;box-shadow:0 0 0 4px #fff1df}.timeline section{flex:1}.timeline b{font-size:11px}.timeline p{margin:4px 0;color:#687187;font-size:10px}.timeline small{color:#a1a7b7;font-size:8px}
</style>
