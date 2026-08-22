<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()
const active = computed({ get:() => route.path, set:value => router.push(value) })
</script>

<template>
  <main class="app-shell"><router-view /></main>
  <van-tabbar v-model="active" safe-area-inset-bottom active-color="#5b5fe9" inactive-color="#8790a7">
    <van-tabbar-item name="/" icon="chart-trending-o">总览</van-tabbar-item>
    <van-tabbar-item name="/orders" icon="orders-o">订单</van-tabbar-item>
    <van-tabbar-item name="/fulfillment" icon="logistics">履约</van-tabbar-item>
    <van-tabbar-item name="/profile" icon="manager-o">我的</van-tabbar-item>
  </van-tabbar>
</template>
