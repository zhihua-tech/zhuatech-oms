<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/oms'
const channels = ref([])
const money = value => Number(value||0).toLocaleString('zh-CN')
const totalOrders = computed(() => channels.value.reduce((s,x)=>s+x.todayOrders,0))
const totalAmount = computed(() => channels.value.reduce((s,x)=>s+x.todayAmount,0))
const syncLabels = { NORMAL:'同步正常', WARNING:'同步延迟', OFFLINE:'连接中断' }
onMounted(async () => { channels.value = await api.channels() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">渠道管理</h1><div class="page-subtitle">统一连接电商、自营与线下渠道</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="channel-hero"><div><span>今日渠道订单</span><strong>{{ totalOrders }}</strong></div><i></i><div><span>渠道成交额</span><strong>¥{{ money(totalAmount) }}</strong></div></section>
    <h2 class="section-title">已接入渠道 <small>{{ channels.length }} 个连接器</small></h2>
    <section v-for="channel in channels" :key="channel.id" class="card channel-item">
      <div class="channel-head"><span class="channel-icon">{{ channel.name.slice(0,1) }}</span><div><b>{{ channel.name }}</b><small>{{ channel.code }} · {{ channel.type }}</small></div><span class="sync" :class="{warning:channel.syncStatus==='WARNING'}"><i></i>{{ syncLabels[channel.syncStatus] }}</span></div>
      <div class="channel-stats"><div><span>今日订单</span><b>{{ channel.todayOrders }}</b></div><div><span>成交金额</span><b>¥{{ money(channel.todayAmount) }}</b></div><div><span>最近同步</span><b>{{ channel.lastSyncAt?.slice(11,16) }}</b></div></div>
    </section>
    <section class="connect-tip"><van-icon name="link-o" /><div><b>标准渠道连接器</b><p>支持通过开放 API、Webhook 或文件任务扩展更多订单来源。</p></div></section>
  </div>
</template>

<style scoped>
.channel-hero{display:grid;grid-template-columns:1fr 1px 1.3fr;gap:18px;padding:20px;border-radius:23px;color:#fff;background:linear-gradient(135deg,#171d48,#4e55ca)}.channel-hero>div{display:flex;flex-direction:column}.channel-hero span{color:#bdc4f4;font-size:9px}.channel-hero strong{margin-top:7px;font-size:21px}.channel-hero>i{width:1px;background:rgba(255,255,255,.18)}.channel-item{padding:15px}.channel-head{display:flex;align-items:center;gap:10px}.channel-icon{width:42px;height:42px;display:grid;place-items:center;border-radius:14px;background:#eceeff;color:#575cdb;font-size:14px;font-weight:900}.channel-head>div{display:flex;flex:1;flex-direction:column}.channel-head b{font-size:12px}.channel-head small{margin-top:4px;color:#979eb1;font-size:8px}.sync{color:#279d8b;font-size:8px}.sync i{display:inline-block;width:6px;height:6px;margin-right:4px;border-radius:50%;background:#35c4ad}.sync.warning{color:#d8792b}.sync.warning i{background:#ef9f4f}.channel-stats{display:grid;grid-template-columns:repeat(3,1fr);margin-top:14px;padding-top:13px;border-top:1px dashed var(--line)}.channel-stats div{display:flex;flex-direction:column;border-right:1px solid var(--line)}.channel-stats div+div{padding-left:12px}.channel-stats div:last-child{border:0}.channel-stats span{color:#979daf;font-size:8px}.channel-stats b{margin-top:5px;font-size:11px}.connect-tip{display:flex;gap:11px;padding:15px;border-radius:18px;background:#e8f8f5;color:#237e71}.connect-tip>.van-icon{font-size:23px}.connect-tip b{font-size:11px}.connect-tip p{margin:4px 0 0;font-size:9px;line-height:1.5}
</style>
