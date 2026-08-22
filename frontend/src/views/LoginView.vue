<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { showSuccessToast } from 'vant'
import { useAuthStore } from '../stores/auth'

const form = reactive({ username:'demo', password:'Demo@2026' })
const loading = ref(false)
const router = useRouter()
const auth = useAuthStore()
async function submit() {
  loading.value = true
  try { await auth.login(form); showSuccessToast('订单网络已连接'); router.replace('/') }
  finally { loading.value = false }
}
</script>

<template>
  <div class="login-page safe-top">
    <div class="brand"><span>ZH</span><div><b>ZHUATECH OMS</b><small>COMMUNITY EDITION</small></div></div>
    <section class="visual">
      <div class="route route-one"><i></i><b>渠道</b><span>314 单</span></div>
      <div class="route route-two"><i></i><b>订单中枢</b><span>实时编排</span></div>
      <div class="route route-three"><i></i><b>履约</b><span>96.8%</span></div>
      <div class="pulse"><span></span></div>
    </section>
    <section class="headline"><span>多渠道订单，一处掌控</span><h1>让每张订单<br>按承诺抵达</h1><p>归集、审单、分仓、发货和售后，全链路可视。</p></section>
    <section class="login-card">
      <van-field v-model="form.username" label="账号" placeholder="请输入账号" left-icon="contact-o" />
      <van-field v-model="form.password" label="密码" type="password" placeholder="请输入密码" left-icon="closed-eye" />
      <van-button block round color="#5b5fe9" :loading="loading" @click="submit">进入订单指挥舱</van-button>
      <div class="demo-tip">运营演示：demo / Demo@2026<br>管理员：admin / ZhuaTech@2026</div>
    </section>
    <a class="company" href="https://www.zhuatech.cn/" target="_blank" rel="noopener">上海如静知华信息科技有限公司</a>
  </div>
</template>

<style scoped>
.login-page{min-height:100vh;padding:25px 22px 28px;color:#fff;background:radial-gradient(circle at 85% 5%,#4250a8 0,transparent 32%),linear-gradient(155deg,#121739 0,#222964 58%,#f5f6fb 58.1%)}.brand{display:flex;align-items:center;gap:11px}.brand>span{width:38px;height:38px;display:grid;place-items:center;border-radius:12px;background:#fff;color:#4f54dc;font-size:12px;font-weight:900}.brand div{display:flex;flex-direction:column}.brand b{font-size:12px;letter-spacing:1.4px}.brand small{margin-top:3px;color:#9ca8ef;font-size:8px;letter-spacing:1px}.visual{position:relative;height:158px;margin:25px 0 5px}.pulse{position:absolute;left:50%;top:50%;width:74px;height:74px;transform:translate(-50%,-50%);border:1px solid rgba(131,143,255,.35);border-radius:50%;box-shadow:0 0 0 18px rgba(120,132,255,.08),0 0 0 38px rgba(120,132,255,.045)}.pulse span{position:absolute;left:50%;top:50%;width:14px;height:14px;transform:translate(-50%,-50%);border-radius:50%;background:#91e9dc;box-shadow:0 0 20px #91e9dc}.route{position:absolute;z-index:2;display:flex;align-items:center;gap:7px;padding:9px 11px;border:1px solid rgba(255,255,255,.13);border-radius:13px;background:rgba(255,255,255,.08);backdrop-filter:blur(8px);font-size:9px}.route i{width:7px;height:7px;border-radius:50%;background:#8fe8dc}.route b{font-size:10px}.route span{color:#aeb8f4}.route-one{left:0;top:12px}.route-two{right:0;top:57px}.route-three{left:18px;bottom:8px}.headline>span{color:#8fe8dc;font-size:10px;letter-spacing:1px}.headline h1{margin:8px 0 6px;font-size:34px;line-height:1.18;letter-spacing:-1px}.headline p{margin:0;color:#b8c1ed;font-size:11px}.login-card{margin-top:24px;padding:19px 17px 17px;border-radius:23px;background:#fff;box-shadow:0 22px 50px rgba(12,18,57,.24)}.van-button{margin-top:17px}.demo-tip{text-align:center;color:#929bb1;font-size:10px;line-height:1.75;margin-top:11px}.company{display:block;margin-top:21px;text-align:center;color:#778097;text-decoration:none;font-size:10px}
</style>
