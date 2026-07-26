<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { onMounted, ref } from 'vue'
import { api } from '../api/oms'
const dashboard = ref({})
const channels = ref([])
const bars = [48,62,57,73,68,91,84]
const money = value => Number(value||0).toLocaleString('zh-CN')
onMounted(async () => { [dashboard.value, channels.value] = await Promise.all([api.dashboard(), api.channels()]) })
</script>
<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">订单分析</h1><div class="page-subtitle">订单规模、履约质量与渠道表现</div></div><span class="head-action"><van-icon name="calendar-o" size="20" /></span></div>
    <section class="metric-grid"><div><span>今日 GMV</span><strong>¥{{ money(dashboard.todayGmv) }}</strong><small>↗ 18.6%</small></div><div><span>平均客单价</span><strong>¥1,581</strong><small>↗ 5.2%</small></div><div><span>履约及时率</span><strong>{{ dashboard.fulfillmentRate }}%</strong><small>目标 95%</small></div><div><span>售后率</span><strong>1.9%</strong><small>↓ 0.4%</small></div></section>
    <h2 class="section-title">近 7 日订单趋势 <small>订单量</small></h2>
    <section class="card chart"><div class="bars"><i v-for="(h,i) in bars" :key="i" :style="{height:h+'%'}"><em></em></i></div><div class="axis"><span v-for="d in ['一','二','三','四','五','六','日']" :key="d">{{ d }}</span></div></section>
    <h2 class="section-title">渠道排行 <small>按成交额</small></h2>
    <section class="card ranking"><div v-for="(channel,index) in channels" :key="channel.id"><span class="rank">{{ index+1 }}</span><b>{{ channel.name }}</b><i><em :style="{width:(100-index*18)+'%'}"></em></i><strong>¥{{ money(channel.todayAmount) }}</strong></div></section>
  </div>
</template>
<style scoped>
.metric-grid{display:grid;grid-template-columns:repeat(2,1fr);gap:10px}.metric-grid>div{display:flex;flex-direction:column;padding:16px;border:1px solid var(--line);border-radius:18px;background:#fff}.metric-grid span{color:#8c93a8;font-size:9px}.metric-grid strong{margin:7px 0 5px;font-size:19px}.metric-grid small{color:#289d8b;font-size:8px}.chart{height:190px}.bars{height:135px;display:flex;align-items:flex-end;justify-content:space-around;gap:11px}.bars i{width:20px;border-radius:7px 7px 3px 3px;background:#edeefe}.bars em{display:block;width:100%;height:74%;border-radius:7px 7px 3px 3px;background:linear-gradient(180deg,#7175f2,#4b50cd)}.axis{display:flex;justify-content:space-around;margin-top:9px;color:#979eb0;font-size:9px}.ranking>div{display:grid;grid-template-columns:22px 90px 1fr 74px;align-items:center;gap:7px;padding:10px 0}.rank{width:20px;height:20px;display:grid;place-items:center;border-radius:7px;background:#eceeff;color:#565bda;font-size:9px}.ranking b{font-size:10px}.ranking i{height:5px;border-radius:99px;background:#eceef4}.ranking em{display:block;height:100%;border-radius:99px;background:#56cabb}.ranking strong{text-align:right;font-size:9px}
</style>
