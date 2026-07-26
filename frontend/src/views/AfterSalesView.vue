<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/oms'
const requests = ref([])
const active = ref('ALL')
const filters = [['ALL','全部'],['PENDING','待审核'],['PROCESSING','处理中'],['COMPLETED','已完成']]
const typeLabels = { REFUND_ONLY:'仅退款', RETURN_REFUND:'退货退款', EXCHANGE:'换货' }
const statusLabels = { PENDING:'待审核', APPROVED:'已通过', REJECTED:'已拒绝', PROCESSING:'处理中', COMPLETED:'已完成' }
const visible = computed(() => active.value === 'ALL' ? requests.value : requests.value.filter(x => x.status === active.value))
const pendingAmount = computed(() => requests.value.filter(x => x.status !== 'COMPLETED').reduce((s,x)=>s+x.amount,0))
const money = value => Number(value||0).toLocaleString('zh-CN')
onMounted(async () => { requests.value = await api.afterSales() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">售后中心</h1><div class="page-subtitle">退款、退货与换货协同处理</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="after-summary"><div><span>待处理金额</span><strong>¥{{ money(pendingAmount) }}</strong></div><div><span>处理中</span><strong>{{ requests.filter(x=>x.status!=='COMPLETED').length }} <small>单</small></strong></div></section>
    <div class="filter-strip"><button v-for="item in filters" :key="item[0]" class="filter-chip" :class="{active:active===item[0]}" @click="active=item[0]">{{ item[1] }}</button></div>
    <section v-for="request in visible" :key="request.id" class="card after-card">
      <div class="list-top"><div><div class="list-title">{{ request.customerName }}</div><div class="list-code">{{ request.requestNo }} · {{ request.orderNo }}</div></div><span class="tag" :class="{orange:request.status==='PENDING',red:request.status==='PROCESSING'}">{{ statusLabels[request.status] }}</span></div>
      <div class="reason"><span>{{ typeLabels[request.type] }}</span><p>{{ request.reason }}</p></div>
      <div class="amount-row"><div><span class="muted">申请金额</span><strong>¥{{ money(request.amount) }}</strong></div><div class="handler"><span>处理人</span><b>{{ request.handlerName }}</b></div></div>
    </section>
  </div>
</template>

<style scoped>
.after-summary{display:grid;grid-template-columns:1.45fr 1fr;gap:10px;margin-bottom:18px}.after-summary>div{display:flex;flex-direction:column;padding:17px;border-radius:19px;background:#fff;border:1px solid var(--line)}.after-summary>div:first-child{color:#fff;background:linear-gradient(135deg,#22285f,#5c61e8)}.after-summary span{font-size:9px;color:#9ba2b8}.after-summary>div:first-child span{color:#c8cdfb}.after-summary strong{margin-top:7px;font-size:21px}.after-summary small{font-size:9px}.reason{margin-top:14px;padding:12px;border-radius:13px;background:#f7f7fb}.reason span{padding:3px 7px;border-radius:7px;background:#e9eaff;color:#5358d1;font-size:9px}.reason p{margin:8px 0 0;color:#596079;font-size:11px}.handler{display:flex;flex-direction:column;text-align:right}.handler span{color:#9298aa;font-size:9px}.handler b{margin-top:5px;color:#596079;font-size:10px}
</style>
