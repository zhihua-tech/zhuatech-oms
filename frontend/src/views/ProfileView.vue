<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()
const router = useRouter()
const roleName = { ADMIN:'系统管理员', MANAGER:'订单运营经理', SALES:'订单运营专员', PURCHASING:'采购专员', FINANCE:'财务专员', WAREHOUSE:'履约专员', VIEWER:'只读成员' }
function logout() { auth.logout(); router.replace('/login') }
</script>

<template>
  <div class="page safe-top profile-page">
    <section class="profile-head"><div class="profile-logo">{{ auth.user?.fullName?.slice(0,1) }}</div><div><h1>{{ auth.user?.fullName }}</h1><p>{{ roleName[auth.user?.role] }} · {{ auth.user?.department }}</p></div><span class="tag">在线</span></section>
    <section class="card account-card"><div><span>今日处理</span><b>42</b><small>订单任务</small></div><div><span>待办</span><b>6</b><small>需要关注</small></div><div><span>数据权限</span><b>全域</b><small>订单视图</small></div></section>
    <section class="card menu-card"><van-cell title="账号与安全" icon="shield-o" is-link /><van-cell title="消息与待办" icon="bell" value="6" is-link /><van-cell title="渠道同步" icon="exchange" value="刚刚" is-link /><van-cell title="关于知华 OMS" icon="info-o" is-link /></section>
    <section class="brand-panel"><span class="mini-mark">ZH</span><div><b>ZhuaTech OMS Community</b><a href="https://www.zhuatech.cn/" target="_blank" rel="noopener">上海如静知华信息科技有限公司</a></div></section>
    <button class="logout" @click="logout">退出登录</button>
    <p class="license-tip">仅供个人非商业学习交流，商业使用须取得书面授权</p>
  </div>
</template>

<style scoped>
.profile-head{display:flex;align-items:center;gap:13px;margin:7px 2px 21px}.profile-logo{width:58px;height:58px;display:grid;place-items:center;border-radius:19px;color:#5be0d5;background:linear-gradient(135deg,var(--navy),#24576a);font-size:23px;font-weight:800}.profile-head>div:nth-child(2){flex:1}.profile-head h1{font-size:21px;margin:0;color:var(--navy)}.profile-head p{margin:5px 0 0;color:var(--muted);font-size:11px}.account-card{display:grid;grid-template-columns:repeat(3,1fr);text-align:center}.account-card div{display:flex;flex-direction:column;border-right:1px solid var(--line)}.account-card div:last-child{border:0}.account-card span,.account-card small{font-size:9px;color:#8c9da4}.account-card b{font-size:18px;margin:5px 0;color:var(--navy)}.menu-card{padding:4px}.brand-panel{display:flex;align-items:center;gap:11px;padding:15px;border-radius:18px;background:#e6f3f2}.mini-mark{width:38px;height:38px;display:grid;place-items:center;border-radius:12px;background:var(--navy);color:#55d8cc;font-size:11px;font-weight:800}.brand-panel div{display:flex;flex-direction:column;gap:4px}.brand-panel b{font-size:12px;color:var(--navy)}.brand-panel a{color:#61808a;text-decoration:none;font-size:9px}.logout{width:100%;height:46px;margin-top:16px;border:1px solid #dfe8ea;border-radius:14px;background:#fff;color:#d2544b}.license-tip{text-align:center;color:#9aabb1;font-size:9px;margin-top:18px}
</style>
