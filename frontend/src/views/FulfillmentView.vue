<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/oms'
const shipments = ref([])
const labels = { PENDING:'待处理', PICKING:'拣货中', PACKED:'已打包', SHIPPED:'运输中', DELIVERED:'已签收', EXCEPTION:'异常' }
const tones = { PENDING:'gray', PICKING:'orange', PACKED:'purple', SHIPPED:'', DELIVERED:'', EXCEPTION:'red' }
const pending = computed(() => shipments.value.filter(x => ['PENDING','PICKING','PACKED'].includes(x.status)).length)
onMounted(async () => { shipments.value = await api.shipments() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">履约发运</h1><div class="page-subtitle">分仓、拣配、交接与物流追踪</div></div><span class="head-action"><van-icon name="scan" size="20" /></span></div>
    <section class="fulfill-hero"><div><span>待发运任务</span><strong>{{ pending }}</strong><small>个包裹等待仓库处理</small></div><div class="ring"><b>96.8%</b><span>及时履约</span></div></section>
    <section class="steps"><div class="active"><i>1</i><span>分仓</span></div><em></em><div class="active"><i>2</i><span>拣货</span></div><em></em><div><i>3</i><span>打包</span></div><em></em><div><i>4</i><span>交接</span></div></section>
    <h2 class="section-title">发运任务 <small>按承诺时间排序</small></h2>
    <section v-for="shipment in shipments" :key="shipment.id" class="card shipment-card" :class="{exception:shipment.status==='EXCEPTION'}">
      <div class="list-top"><div><div class="list-title">{{ shipment.warehouseName }}</div><div class="list-code">{{ shipment.shipmentNo }}</div></div><span class="tag" :class="tones[shipment.status]">{{ labels[shipment.status] }}</span></div>
      <div class="route"><span class="box"><van-icon name="logistics" /></span><div><b>{{ shipment.orderNo }}</b><small>{{ shipment.itemCount }} 件商品 · {{ shipment.carrierName }}</small></div><van-icon name="arrow" /></div>
      <div v-if="shipment.status==='EXCEPTION'" class="warning"><van-icon name="warning-o" /> 库存不足，等待跨仓调拨</div>
      <div v-else class="meta-row"><span>运单：{{ shipment.trackingNo || '出库后生成' }}</span><span>预计送达：{{ shipment.expectedDeliveryAt?.slice(5,10) }}</span></div>
    </section>
  </div>
</template>

<style scoped>
.fulfill-hero{display:flex;align-items:center;justify-content:space-between;padding:20px;border-radius:23px;color:#fff;background:linear-gradient(135deg,#181e4d,#3d45a8)}.fulfill-hero>div:first-child{display:flex;flex-direction:column}.fulfill-hero span{color:#c0c6f3;font-size:10px}.fulfill-hero strong{margin:5px 0;font-size:34px}.fulfill-hero small{color:#99a3df;font-size:9px}.ring{width:76px;height:76px;display:flex;flex-direction:column;align-items:center;justify-content:center;border:7px solid rgba(157,241,226,.25);border-top-color:#8de8d9;border-radius:50%}.ring b{font-size:15px}.ring span{font-size:8px}.steps{display:flex;align-items:flex-start;justify-content:center;margin:17px 3px}.steps div{display:flex;flex-direction:column;align-items:center;gap:5px;color:#959bae;font-size:8px}.steps i{width:25px;height:25px;display:grid;place-items:center;border-radius:50%;background:#eaecf3;font-style:normal}.steps .active i{color:#fff;background:#5b5fe9}.steps em{width:43px;height:2px;margin-top:12px;background:#e1e4ed}.shipment-card.exception{border-color:#ffd6c9}.route{display:flex;align-items:center;gap:10px;margin-top:15px;padding:12px;border-radius:14px;background:#f6f7fb}.route .box{width:35px;height:35px;display:grid;place-items:center;border-radius:11px;background:#e6f7f4;color:#21a892;font-size:18px}.route div{flex:1;display:flex;flex-direction:column;gap:4px}.route b{font-size:11px}.route small{color:#8d94a8;font-size:9px}.route>.van-icon{color:#a4a9b9}.warning{margin-top:11px;padding:9px 11px;border-radius:10px;background:#fff0eb;color:#d66343;font-size:9px}
</style>
