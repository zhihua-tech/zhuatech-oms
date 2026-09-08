/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;
import org.junit.jupiter.api.Test;import java.math.BigDecimal;import static org.assertj.core.api.Assertions.assertThat;
class FulfillmentExceptionGovernanceServiceTest{
 private final FulfillmentExceptionGovernanceService service=new FulfillmentExceptionGovernanceService();
 private FulfillmentExceptionGovernanceService.Request request(boolean notify,boolean evidence,boolean sla){return new FulfillmentExceptionGovernanceService.Request("EX-1","owner","approver",BigDecimal.ZERO,true,true,true,false,true,false,true,true,true,notify,evidence,sla);}
 @Test void recoversControlledException(){assertThat(service.assess(request(true,true,true)).decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.RECOVER);}
 @Test void reviewsCustomerAndSlaActions(){var a=service.assess(request(false,false,false));assertThat(a.decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.REVIEW);assertThat(a.actions()).hasSize(3);}
 @Test void blocksUnsafeRouteAndRefund(){var r=new FulfillmentExceptionGovernanceService.Request("EX-2","u1","u1",new BigDecimal("5000"),false,false,false,true,false,true,false,false,false,true,true,true);var a=service.assess(r);assertThat(a.decision()).isEqualTo(FulfillmentExceptionGovernanceService.Decision.BLOCKED);assertThat(a.riskLevel()).isEqualTo(FulfillmentExceptionGovernanceService.RiskLevel.HIGH);assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(7);}
}
