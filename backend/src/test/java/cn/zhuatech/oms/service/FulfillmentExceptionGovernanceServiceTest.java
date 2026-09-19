/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;
import org.junit.jupiter.api.Test;import java.math.BigDecimal;import static org.assertj.core.api.Assertions.assertThat;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class FulfillmentExceptionGovernanceServiceTest{
 private final FulfillmentExceptionGovernanceService service=new FulfillmentExceptionGovernanceService();
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private FulfillmentExceptionGovernanceService.Request request(boolean notify,boolean evidence,boolean sla){return new FulfillmentExceptionGovernanceService.Request("EX-1","owner","approver",BigDecimal.ZERO,true,true,true,false,true,false,true,true,true,notify,evidence,sla);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void recoversControlledException(){assertThat(service.assess(request(true,true,true)).decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.RECOVER);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void reviewsCustomerAndSlaActions(){var a=service.assess(request(false,false,false));assertThat(a.decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.REVIEW);assertThat(a.actions()).hasSize(3);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void blocksUnsafeRouteAndRefund(){var r=new FulfillmentExceptionGovernanceService.Request("EX-2","u1","u1",new BigDecimal("5000"),false,false,false,true,false,true,false,false,false,true,true,true);var a=service.assess(r);assertThat(a.decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.BLOCKED);assertThat(a.riskLevel()).isEqualTo(FulfillmentExceptionGovernanceService.RiskLevel.HIGH);assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(7);}
}
