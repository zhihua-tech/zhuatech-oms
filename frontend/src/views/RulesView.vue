<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
const rules = [
  { name:'华东区域智能分仓', scene:'订单路由', desc:'上海、江苏、浙江订单优先选择库存充足且距离最近的仓库', count:186, color:'purple' },
  { name:'高价值订单人工复核', scene:'风险控制', desc:'实付金额超过 ¥50,000 时进入运营经理复核队列', count:7, color:'orange' },
  { name:'24 小时发货承诺', scene:'履约时效', desc:'根据渠道截单时间自动计算最晚出库时间并预警', count:314, color:'cyan' },
  { name:'售后自动分派', scene:'售后服务', desc:'按商品类目和责任仓库分派售后处理人', count:6, color:'blue' }
]
</script>
<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">订单规则</h1><div class="page-subtitle">用规则驱动自动审单与智能履约</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="rule-hero"><van-icon name="setting-o" /><div><b>规则引擎运行正常</b><p>今日自动处理 513 次，人工介入率 2.3%</p></div><span>在线</span></section>
    <h2 class="section-title">已启用规则 <small>{{ rules.length }} 条</small></h2>
    <section v-for="rule in rules" :key="rule.name" class="card rule-card"><span class="rule-icon" :class="rule.color"><van-icon name="cluster-o" /></span><div><header><b>{{ rule.name }}</b><span>{{ rule.scene }}</span></header><p>{{ rule.desc }}</p><small>今日命中 {{ rule.count }} 次</small></div><van-switch :model-value="true" size="18" active-color="#5b5fe9" /></section>
  </div>
</template>
<style scoped>
.rule-hero{display:flex;align-items:center;gap:11px;padding:16px;border-radius:20px;color:#fff;background:linear-gradient(135deg,#1a204f,#4d54c8)}.rule-hero>.van-icon{font-size:27px;color:#98efe2}.rule-hero div{flex:1}.rule-hero b{font-size:12px}.rule-hero p{margin:4px 0 0;color:#bfc5f2;font-size:9px}.rule-hero>span{padding:4px 8px;border-radius:99px;background:rgba(142,235,221,.15);color:#96eee1;font-size:8px}.rule-card{display:flex;align-items:flex-start;gap:11px;padding:15px}.rule-icon{flex:none;width:38px;height:38px;display:grid;place-items:center;border-radius:12px;background:#eceeff;color:#5b5fe9;font-size:18px}.rule-icon.orange{background:#fff0e2;color:#dd7a1c}.rule-icon.cyan{background:#e3f7f4;color:#229f8c}.rule-icon.blue{background:#e8f2fb;color:#3979ae}.rule-card>div{flex:1}.rule-card header{display:flex;align-items:center;gap:7px}.rule-card header b{font-size:11px}.rule-card header span{padding:3px 6px;border-radius:6px;background:#f0f1f6;color:#788096;font-size:7px}.rule-card p{margin:6px 0;color:#70778d;font-size:9px;line-height:1.5}.rule-card small{color:#9ca2b3;font-size:8px}
</style>
