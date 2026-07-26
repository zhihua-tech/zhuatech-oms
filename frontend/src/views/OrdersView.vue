<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/oms'
const orders = ref([])
const active = ref('ALL')
const keyword = ref('')
const filters = [['ALL','全部'],['PAID','待分仓'],['ALLOCATING','分仓中'],['READY_TO_SHIP','待发货'],['SHIPPED','已发货'],['AFTER_SALE','售后中']]
const labels = { PENDING_PAYMENT:'待支付', PAID:'待分仓', ALLOCATING:'分仓中', READY_TO_SHIP:'待发货', SHIPPED:'已发货', COMPLETED:'已完成', CANCELLED:'已取消', AFTER_SALE:'售后中' }
const tones = { PAID:'orange', ALLOCATING:'orange', READY_TO_SHIP:'purple', SHIPPED:'', COMPLETED:'', CANCELLED:'gray', AFTER_SALE:'red' }
const visible = computed(() => orders.value.filter(item => (active.value === 'ALL' || item.status === active.value) && (!keyword.value || `${item.orderNo}${item.customerName}${item.itemSummary}`.includes(keyword.value))))
const money = value => Number(value || 0).toLocaleString('zh-CN')
onMounted(async () => { orders.value = await api.orders() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">订单中心</h1><div class="page-subtitle">全渠道订单统一归集与状态编排</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <van-search v-model="keyword" placeholder="搜索订单号、客户或商品" shape="round" background="transparent" />
    <div class="filter-strip"><button v-for="item in filters" :key="item[0]" class="filter-chip" :class="{active:active===item[0]}" @click="active=item[0]">{{ item[1] }}</button></div>
    <div class="result-note"><span>共 {{ visible.length }} 张订单</span><button>智能排序 <van-icon name="arrow-down" /></button></div>
    <section v-for="order in visible" :key="order.id" class="card order-card">
      <div class="list-top"><div><div class="channel"><i></i>{{ order.channelName }}</div><div class="list-code">{{ order.orderNo }}</div></div><span class="tag" :class="tones[order.status]">{{ labels[order.status] }}</span></div>
      <div class="customer"><b>{{ order.customerName }}</b><span>{{ order.customerPhone }}</span></div>
      <div class="goods"><span class="goods-icon"><van-icon name="bag-o" /></span><div><b>{{ order.itemSummary }}</b><small>{{ order.warehouseName }} · 共 {{ order.itemCount }} 件</small></div></div>
      <div class="amount-row"><div><span>实付金额</span><strong>¥{{ money(order.paidAmount) }}</strong></div><div class="promise"><span>承诺发货</span><b>{{ order.promisedShipAt?.slice(5,16).replace('T',' ') }}</b></div></div>
    </section>
  </div>
</template>

<style scoped>
.van-search{margin:0 -10px 11px}.result-note{display:flex;justify-content:space-between;align-items:center;margin:0 2px 11px;color:#8a91a5;font-size:10px}.result-note button{border:0;background:none;color:#727b96;font-size:10px}.order-card{padding:16px}.channel{display:flex;align-items:center;gap:6px;color:#444b6e;font-size:11px;font-weight:700}.channel i{width:7px;height:7px;border-radius:50%;background:#5b5fe9}.tag.purple{color:#4c50ca;background:#ececff}.customer{display:flex;justify-content:space-between;margin-top:14px}.customer b{font-size:13px}.customer span{color:#9097aa;font-size:10px}.goods{display:flex;gap:10px;margin-top:12px;padding:11px;border-radius:14px;background:#f6f7fb}.goods-icon{flex:none;width:34px;height:34px;display:grid;place-items:center;border-radius:11px;background:#e9ebff;color:#565bdd}.goods div{display:flex;flex-direction:column;gap:5px}.goods b{font-size:11px;line-height:1.4}.goods small{color:#9097aa;font-size:9px}.amount-row>div{display:flex;flex-direction:column}.amount-row span{color:#8b92a7;font-size:9px}.amount-row strong{margin-top:4px;font-size:18px}.promise{text-align:right}.promise b{margin-top:5px;color:#5f6682;font-size:10px}
</style>
