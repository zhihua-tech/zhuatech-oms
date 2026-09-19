/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oms.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class OrderReleaseGovernanceServiceTest {
    private final OrderReleaseGovernanceService service = new OrderReleaseGovernanceService();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void releasesControlledOrder() {
        var result = service.assess(new OrderReleaseGovernanceService.Request(
                "ORD-001", true, true, true, true, true, true, true, false, false));
        assertThat(result.decision()).isEqualTo(OrderReleaseGovernanceService.Decision.RELEASE);
        assertThat(result.blockers()).isEmpty();
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void holdsFraudAndComplianceFailure() {
        var result = service.assess(new OrderReleaseGovernanceService.Request(
                "ORD-002", false, false, false, false, false, false, false, true, false));
        assertThat(result.decision()).isEqualTo(OrderReleaseGovernanceService.Decision.HOLD);
        assertThat(result.blockers()).hasSize(5);
        assertThat(result.actions()).hasSize(3);
    }
}
