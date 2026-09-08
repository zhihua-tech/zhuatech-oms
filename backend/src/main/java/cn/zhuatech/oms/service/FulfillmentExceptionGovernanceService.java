/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.util.*;
@Service
public class FulfillmentExceptionGovernanceService{
 public Assessment assess(Request r){
  List<String> blockers=new ArrayList<>();List<String> actions=new ArrayList<>();
  if(!r.inventoryPromiseRecalculated())blockers.add("异常后未重新计算库存承诺");
  if(!r.paymentRiskCleared())blockers.add("支付或反欺诈风险尚未解除");
  if(!r.carrierCapacityConfirmed())blockers.add("调整后的承运能力未确认");
  if(r.routeChanged()&&!r.addressComplianceRechecked())blockers.add("改路后必须重新校验地址与区域合规");
  if(r.splitShipment()&&!r.splitAuthorized())blockers.add("拆单发运未获得客户策略授权");
  if(r.refundAmount().compareTo(BigDecimal.ZERO)>0&&!r.refundApproved())blockers.add("补偿退款尚未通过授权审批");
  if(!r.idempotencyKeyRegistered())blockers.add("恢复指令缺少幂等键");
  if(r.ownerId().equals(r.approverId()))blockers.add("异常负责人不得审批自己的高风险恢复方案");
  if(!r.customerNotified())actions.add("向客户发送新的交付承诺与选择项");
  if(!r.auditEvidenceAttached())actions.add("补充异常原因、方案版本和审批证据");
  if(!r.slaTimerReset())actions.add("重置履约 SLA 计时并登记豁免原因");
  RiskLevel risk=r.refundAmount().compareTo(new BigDecimal("1000"))>0||r.routeChanged()||r.splitShipment()?RiskLevel.HIGH:RiskLevel.NORMAL;
  Decision decision=!blockers.isEmpty()?Decision.BLOCKED:!actions.isEmpty()?Decision.REVIEW:Decision.RECOVER;
  String route=risk==RiskLevel.HIGH?"履约主管→风控/财务→客服负责人":"履约主管";
  return new Assessment(r.exceptionNo(),decision,risk,route,List.copyOf(blockers),List.copyOf(actions));
 }
 public record Request(@NotBlank String exceptionNo,@NotBlank String ownerId,@NotBlank String approverId,
  @NotNull @DecimalMin("0.00") BigDecimal refundAmount,boolean inventoryPromiseRecalculated,boolean paymentRiskCleared,
  boolean carrierCapacityConfirmed,boolean routeChanged,boolean addressComplianceRechecked,boolean splitShipment,
  boolean splitAuthorized,boolean refundApproved,boolean idempotencyKeyRegistered,boolean customerNotified,
  boolean auditEvidenceAttached,boolean slaTimerReset){}
 public record Assessment(String exceptionNo,Decision decision,RiskLevel riskLevel,String approvalRoute,List<String> blockers,List<String> actions){}
 public enum Decision{RECOVER,REVIEW,BLOCKED}public enum RiskLevel{NORMAL,HIGH}
}
